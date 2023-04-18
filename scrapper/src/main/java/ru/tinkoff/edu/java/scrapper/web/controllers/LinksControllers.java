package ru.tinkoff.edu.java.scrapper.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.services.LinkService;

import java.net.URI;

@RestController
@RequestMapping(value = "/links")
public class LinksControllers {

    @Autowired
    LinkService service;

    @GetMapping
    @Operation(summary = "Получить все отслеживаемые ссылки")
    public ResponseEntity<?> getLinks(@RequestHeader("Tg-Chat-Id") int chatId)
    {
        return service.listAll(chatId);
    }

    @PostMapping
    @Operation(summary = "Добавить отслеживание ссылки")
    public ResponseEntity<?> postLink(@RequestHeader("Tg-Chat-Id") int chatId, @Valid @RequestBody AddLinkRequest request)
    {
        return service.add(chatId, URI.create(request.link()));
    }

    @DeleteMapping
    @Operation(summary = "Убрать отслеживание ссылки")
    public ResponseEntity<?> deleteLink(@RequestHeader("Tg-Chat-Id") int chatId,@Valid @RequestBody RemoveLinkRequest request)
    {
        return service.remove(chatId,URI.create(request.link()));
    }
}
