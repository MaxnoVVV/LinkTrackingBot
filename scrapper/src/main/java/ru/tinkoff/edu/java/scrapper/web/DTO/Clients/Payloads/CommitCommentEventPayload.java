package ru.tinkoff.edu.java.scrapper.web.dto.clients.payloads;

import ru.tinkoff.edu.java.scrapper.web.dto.clients.Comment;

public record CommitCommentEventPayload(String action, Comment comment) implements Payload {
}
