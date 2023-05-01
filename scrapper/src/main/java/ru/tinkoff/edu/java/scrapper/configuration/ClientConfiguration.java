package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.web.client.BotClient;
import ru.tinkoff.edu.java.scrapper.web.client.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.web.client.StackOverFlowWebClient;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.HttpService;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.SendUpdatesService;

@Configuration
public class ClientConfiguration {
    @Bean
    public GitHubWebClient gitHubWebClient()
    {
        return new GitHubWebClient();
    }

    @Bean
    public StackOverFlowWebClient stackOverFlowWebClient()
    {
        return new StackOverFlowWebClient();
    }

    @Bean
    public SendUpdatesService sendUpdatesService(BotClient botClient)
    {
        return new HttpService(botClient);
    }

}
