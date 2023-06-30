package ru.tinkoff.edu.java.scrapper.web.service;

import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.repository.Link;

import java.net.URI;
import java.util.List;

public interface LinkService {

    int update(long tgChatId,String link);
    ResponseEntity<?> add(long tgChatId, URI uri);
    ResponseEntity<?> remove(long tgChatId, URI uri);

    public ResponseEntity<?> listAll(long tgChatId);

    public List<Link> findAll();
}
