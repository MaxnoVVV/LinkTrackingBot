package ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto;

public record LinkUpdateRequest(int id, String url, String description, long[] thChatIds) {
}
