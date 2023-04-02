package ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Payloads;

import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.User;

public record MebmerEventPayload(String action, User member) {
}
