package ru.tinkoff.edu.java.bot.web.dto;


public record LinkUpdateRequest(int id, String url, String description, long[] tgChatIds) {

}
