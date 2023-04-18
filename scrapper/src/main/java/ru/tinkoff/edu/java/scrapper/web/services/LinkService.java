package ru.tinkoff.edu.java.scrapper.web.services;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface LinkService {
    int update();
    ResponseEntity<?> add(long tgChatId, URI uri);
    ResponseEntity<?> remove(long tgChatId, URI uri);
    public ResponseEntity<?> listAll(long tgChatId);
}
