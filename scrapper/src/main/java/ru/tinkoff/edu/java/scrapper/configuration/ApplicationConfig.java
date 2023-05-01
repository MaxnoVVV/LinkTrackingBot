package ru.tinkoff.edu.java.scrapper.configuration;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.BaseParser;
import ru.tinkoff.edu.java.GitHubParser;
import ru.tinkoff.edu.java.Parser;
import ru.tinkoff.edu.java.StackOverFlowParser;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,Scheduler scheduler,RabbitMq rabbitmq,AccessType databaseAccessType,boolean useQueue) {
}
enum AccessType {
    JDBC, JPA
}
record Scheduler(Duration interval) {}
record Queue(String name) {};
record Exchange(String name) {};
record Binding(String routingKey){};
record RabbitMq(Queue queue,Exchange exchange, Binding binding) {};

