package ru.tinkoff.edu.java.scrapper.web.dto.controllers;

public record ListLinksResponse(int size, LinkResponse[] links) {
}
