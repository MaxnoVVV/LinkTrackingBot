package ru.tinkoff.edu.java.scrapper.web.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.GitHubResult;
import ru.tinkoff.edu.java.ParseResult;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.StackOverFlowResult;
import ru.tinkoff.edu.java.scrapper.web.client.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.web.client.StackOverFlowWebClient;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.Item;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.events.CommonEvent;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;
import ru.tinkoff.edu.java.scrapper.web.service.LinkService;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.SendUpdatesService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

  static int id = 0;

  private final GitHubWebClient gitHubWebClient;

  private final StackOverFlowWebClient stackOverFlowWebClient;

  private final SendUpdatesService sendUpdatesService;

  private final LinkService linkService;

  private final Parser parser;

  List<Link> links;

  @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
  public void update() {
    log.info("update");
    try {
      links = linkService.findAll();
      links = links.stream().filter(
          l -> Duration.between(l.getLastCheck(), OffsetDateTime.now(ZoneOffset.UTC)).toMinutes()
              > 15).toList();
      for (Link link : links) {
        ParseResult result = parser.parse(link.getLink());
        ResponseEntity clientResponse = null;
        if (result instanceof StackOverFlowResult) {
          Item[] response = stackOverFlowWebClient.getAnswers(((StackOverFlowResult) result).id())
              .getItems();
          for (Item item : response) {
            if (item.getCreation_date().isAfter(link.getLastCheck())) {
              clientResponse = sendUpdatesService.sendUpdate(
                  new LinkUpdateRequest(id++, link.getLink(),
                      "Появился новый ответ на " + link.getLink(),
                      new long[]{link.trackingUser()}));
            }
          }
        } else {
          List<CommonEvent> events = gitHubWebClient.getInfo(((GitHubResult) result).name(),
              ((GitHubResult) result).repository());
          for (CommonEvent event : events) {
            if (event.getOffsetDataTime().isAfter(link.getLastCheck())) {
              String description = null;
              if (event.getType().equals("CreateEvent")) {    //switch
                description =
                    "В репозитории появился новый " + event.getPayload().get("ref_type") + "\r\n"
                        + link.getLink();
              } else if (event.getType().equals("PushEvent")) {
                description = "В репозитории появился новый push\r\n" + link.getLink();
              } else if (event.getType().equals("PullRequestEvent")) {
                description = "В репозитории появился новый pull request\r\n" + link.getLink();
              }
              if (description != null) {
                clientResponse = sendUpdatesService.sendUpdate(
                    new LinkUpdateRequest(id++, link.getLink(), description,
                        new long[]{link.trackingUser()}));
              }
            }
          }
        }
        if (clientResponse == null || (clientResponse != null && clientResponse.getStatusCode()
            .is2xxSuccessful())) {
          linkService.update(link.trackingUser(), link.getLink());
        }

      }
    } catch (DataAccessException e) {
      log.error(e.toString());
      log.error(e.getMessage());
    }


  }
}
