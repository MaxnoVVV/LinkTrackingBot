package ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Payloads;

public record PullRequestPayload(String action, int number) implements Payload {
}
