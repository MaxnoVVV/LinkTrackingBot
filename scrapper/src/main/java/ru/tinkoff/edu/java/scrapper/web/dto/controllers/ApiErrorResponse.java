package ru.tinkoff.edu.java.scrapper.web.dto.controllers;

public record ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage,
                               String[] stacktrace) {
}
