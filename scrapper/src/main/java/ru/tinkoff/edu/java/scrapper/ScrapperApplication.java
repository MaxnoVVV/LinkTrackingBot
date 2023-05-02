package ru.tinkoff.edu.java.scrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.configuration.DbConfig;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;
import ru.tinkoff.edu.java.scrapper.web.rabbitmq.service.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.web.repository.jdbc.JdbcLinkRepository;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@Import(DbConfig.class)
//@EnableScheduling
public class ScrapperApplication {


    public static void main(String[] args) {
        var ctx = SpringApplication.run(ScrapperApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);
    }
}