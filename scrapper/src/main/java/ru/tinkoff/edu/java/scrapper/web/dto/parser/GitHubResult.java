package ru.tinkoff.edu.java.scrapper.web.dto.parser;

public record GitHubResult(String name, String repository) implements ParseResult {
}