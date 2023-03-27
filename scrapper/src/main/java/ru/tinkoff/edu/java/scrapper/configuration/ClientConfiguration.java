package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.StackOverFlowResponse;
import ru.tinkoff.edu.java.scrapper.web.WebClients.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.web.WebClients.StackOverFlowWebClient;

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

}
