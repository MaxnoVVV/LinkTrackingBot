package ru.tinkoff.edu.java.bot.web.DTO;

public record LinkUpdateRequest(int id, String url, String description, int[] thChatIds) {
}
