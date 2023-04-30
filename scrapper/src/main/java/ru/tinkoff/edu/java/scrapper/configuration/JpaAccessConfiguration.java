package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.scrapper.web.repository.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.web.repository.JpaUserRepository;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;
import ru.tinkoff.edu.java.scrapper.web.services.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.web.services.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app",name = "database-access-type",havingValue = "jpa")
@EnableJpaRepositories(basePackages = "ru.tinkoff.edu.java.scrapper.web.repository")
@Import(ParserConfig.class)
public class JpaAccessConfiguration

{
    @Bean
    public LinkService linkService(JpaLinkRepository repository, Parser parser)
    {
        return new JpaLinkService(repository,parser);
    }

    @Bean
    public TgChatService tgChatService(JpaUserRepository repository)
    {
        return new JpaTgChatService(repository);
    }
}
