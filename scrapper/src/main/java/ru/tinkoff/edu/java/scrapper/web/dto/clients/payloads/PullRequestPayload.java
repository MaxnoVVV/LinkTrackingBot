package ru.tinkoff.edu.java.scrapper.web.dto.clients.payloads;

public record PullRequestPayload(String action, int number) implements Payload {
}
