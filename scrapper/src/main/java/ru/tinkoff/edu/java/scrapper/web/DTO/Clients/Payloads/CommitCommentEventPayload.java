package ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Payloads;

import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Comment;
import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Payloads.Payload;

public record CommitCommentEventPayload(String action, Comment comment) implements Payload {
}
