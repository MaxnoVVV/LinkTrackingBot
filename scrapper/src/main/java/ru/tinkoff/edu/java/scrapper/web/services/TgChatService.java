package ru.tinkoff.edu.java.scrapper.web.services;

import org.springframework.http.ResponseEntity;

public interface TgChatService {
    ResponseEntity<?> register(long tgChatId);
    ResponseEntity<?> unregister(long tgChatId);
}
