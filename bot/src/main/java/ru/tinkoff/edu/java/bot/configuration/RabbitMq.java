package ru.tinkoff.edu.java.bot.configuration;

public record RabbitMq(Queue queue, Exchange exchange, Binding binding) {

}
