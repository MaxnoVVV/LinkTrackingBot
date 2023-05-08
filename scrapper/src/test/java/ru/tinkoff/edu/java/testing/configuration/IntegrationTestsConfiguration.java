package ru.tinkoff.edu.java.testing.configuration;

import com.zaxxer.hikari.util.DriverDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.scrapper.configuration.JdbcAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.configuration.JpaAccessConfiguration;
import ru.tinkoff.edu.java.scrapper.web.repository.JdbcUserRepository;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.web.services.jdbc.JdbcTgChatService;

import javax.sql.DataSource;

@TestConfiguration
@Import({JdbcAccessConfiguration.class, JpaAccessConfiguration.class})
public class IntegrationTestsConfiguration {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate(dataSource());
    }
}
