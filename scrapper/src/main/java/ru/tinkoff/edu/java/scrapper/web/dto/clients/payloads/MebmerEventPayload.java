package ru.tinkoff.edu.java.scrapper.web.dto.clients.payloads;

import ru.tinkoff.edu.java.scrapper.web.dto.clients.User;

public record MebmerEventPayload(String action, User member) {
}
