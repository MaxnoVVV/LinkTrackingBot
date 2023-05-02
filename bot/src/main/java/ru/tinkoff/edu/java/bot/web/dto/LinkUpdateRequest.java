package ru.tinkoff.edu.java.bot.web.dto;

import lombok.ToString;


public record LinkUpdateRequest(int id, String url, String description, long[] thChatIds) {
}
