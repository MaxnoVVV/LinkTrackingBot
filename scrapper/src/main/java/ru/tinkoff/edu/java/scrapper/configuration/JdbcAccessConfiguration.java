package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.BaseParser;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcLinkRepository;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcUserRepository;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app",name = "database-access-type",havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcLinkRepository linkRepository()
    {
        return new JdbcLinkRepository();
    }
    @Bean
    public JdbcUserRepository userRepository()
    {
        return new JdbcUserRepository();
    }

    @Bean
    public LinkService linkService(Parser parser, JdbcLinkRepository linkRepository)
    {
        return new JdbcLinkService(parser,linkRepository);
    }

    @Bean
    public TgChatService tgChatService(JdbcUserRepository userRepository)
    {
        return new JdbcTgChatService(userRepository);
    }
}
