package ru.tinkoff.edu.java.scrapper.web.DTO.controllers;

public record ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage,
                               String[] stacktrace) {
}
