package ru.tinkoff.edu.java.scrapper.web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.Parser;

import java.net.URI;

public interface LinkService {

    int update();
    ResponseEntity<?> add(long tgChatId, URI uri);
    ResponseEntity<?> remove(long tgChatId, URI uri);
    public ResponseEntity<?> listAll(long tgChatId);
}
