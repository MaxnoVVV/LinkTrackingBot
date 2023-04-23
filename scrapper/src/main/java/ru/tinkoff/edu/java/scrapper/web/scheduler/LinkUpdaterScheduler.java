package ru.tinkoff.edu.java.scrapper.web.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Slf4j
@Component
public class LinkUpdaterScheduler {
    static int id = 0;
    @Autowired
    private GitHubWebClient gitHubWebClient;

    @Autowired
    private StackOverFlowWebClient stackOverFlowWebClient;

    @Autowired
    private BotClient botClient;
    @Autowired
    private JdbcLinkRepository jdbcLinkRepository;

    @Autowired
    Parser parser;

    @Scheduled(fixedDelayString = "#{@schedulerIntervalMs}")
    public void update() {
        log.info("update");
        List<Link> links = jdbcLinkRepository.findAll();
        links = links.stream().filter(l -> Duration.between(OffsetDateTime.now(ZoneOffset.UTC),l.getLast_check()).toMinutes() > 15).toList();
        for(Link link : links)
        {
            ParseResult result = parser.parse(link.getLink());

            if(result instanceof StackOverFlowResult)
            {
                Item[] response = stackOverFlowWebClient.getAnswers(((StackOverFlowResult)result).id()).getItems();
                for(Item item : response)
                {
                    if(item.getCreation_date().isAfter(link.getLast_check()))
                    {
                        botClient.sendUpdate(new LinkUpdateRequest(id++,link.getLink(),"update",new long[] {link.tracking_user()}));
                    }
                }
            }
            else
            {
                List<CommonEvent> events = gitHubWebClient.getInfo(((GitHubResult) result).name(),((GitHubResult) result).name());
                for(CommonEvent event : events)
                {
                    if(event.getCreated_at().isAfter(link.getLast_check()))
                    {
                       botClient.sendUpdate(new LinkUpdateRequest(id++,link.getLink(),"update",new long[]{link.tracking_user()}));
                    }
                }
            }
        }
    }
}
