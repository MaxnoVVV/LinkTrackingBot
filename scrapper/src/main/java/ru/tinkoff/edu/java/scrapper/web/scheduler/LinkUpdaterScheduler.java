package ru.tinkoff.edu.java.scrapper.web.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.GitHubResult;
import ru.tinkoff.edu.java.ParseResult;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.StackOverFlowResult;
import ru.tinkoff.edu.java.scrapper.web.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.web.clients.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.web.clients.StackOverFlowWebClient;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.Item;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.events.CommonEvent;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LinkUpdaterScheduler {
    static int id = 0;
    @Autowired
    private GitHubWebClient gitHubWebClient;

    @Autowired
    private StackOverFlowWebClient stackOverFlowWebClient;

    private BotClient botClient = new BotClient();
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Autowired
    Parser parser;


    List<Link> links;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("update");
        try {
            links = jdbcLinkRepository.findAll();
            links = links.stream().filter(l -> Duration.between(l.getLast_check(), OffsetDateTime.now(ZoneOffset.UTC)).toMinutes() > 15).toList();
            for (Link link : links) {
                ParseResult result = parser.parse(link.getLink());
                ResponseEntity clientResponse = null;
                if (result instanceof StackOverFlowResult) {
                    Item[] response = stackOverFlowWebClient.getAnswers(((StackOverFlowResult) result).id()).getItems();
                    for (Item item : response) {
                        if (item.getCreation_date().isAfter(link.getLast_check())) {
                            clientResponse = botClient.sendUpdate(new LinkUpdateRequest(id++, link.getLink(), "Появился новый ответ на " + link.getLink(), new long[]{link.tracking_user()}));
                        }
                    }
                } else {
                    List<CommonEvent> events = gitHubWebClient.getInfo(((GitHubResult) result).name(), ((GitHubResult) result).repository());
                    for (CommonEvent event : events) {
                        if (event.getOffsetDataTime().isAfter(link.getLast_check())) {
                            String description = null;
                            if (event.getType().equals("CreateEvent")) {
                                description = "В репозитории появился новый " + event.getPayload().get("ref_type") + "\r\n" + link.getLink();
                            } else if (event.getType().equals("PushEvent")) {
                                description = "В репозитории появился новый push\r\n" + link.getLink();
                            } else if (event.getType().equals("PullRequestEvent")) {
                                description = "В репозитории появился новый pull request\r\n" + link.getLink();
                            }
                            if (description != null) {
                                clientResponse = botClient.sendUpdate(new LinkUpdateRequest(id++, link.getLink(), description, new long[]{link.tracking_user()}));
                            }
                        }
                    }
                }
                if (clientResponse == null || (clientResponse != null && clientResponse.getStatusCode().is2xxSuccessful())) {
                    jdbcLinkRepository.update(link.tracking_user(), link.getLink());
                }

            }
        } catch (DataAccessException e) {
            log.error(e.toString());
            log.error(e.getMessage());
        }


    }
}
