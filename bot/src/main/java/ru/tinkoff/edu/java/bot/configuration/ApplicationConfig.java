package ru.tinkoff.edu.java.bot.configuration;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test,@NotNull String TOKEN,RabbitMq rabbitmq) {}

record Scheduler(Duration interval) {}
record Queue(String name) {};
record Exchange(String name) {};
record Binding(String routingKey){};
record RabbitMq(Queue queue,Exchange exchange, Binding binding) {};