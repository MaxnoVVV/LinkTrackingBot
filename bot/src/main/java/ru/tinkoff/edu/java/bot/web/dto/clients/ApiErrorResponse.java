package ru.tinkoff.edu.java.bot.web.dto.clients;

public record ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage,
                               String[] stacktrace) {
}
