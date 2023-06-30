package ru.tinkoff.edu.java.scrapper.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.service.LinkService;

import java.net.URI;
@Slf4j
@RestController
@RequestMapping(value = "/links")
public class LinksControllers {

    @Autowired
    @Qualifier("linkService")
    LinkService linkService;

    @GetMapping
    @Operation(summary = "Получить все отслеживаемые ссылки")
    public ResponseEntity<?> getLinks(@RequestHeader("Tg-Chat-Id") long chatId)
    {
        return linkService.listAll(chatId);
    }

    @PostMapping
    @Operation(summary = "Добавить отслеживание ссылки")
    public ResponseEntity<?> postLink(@RequestHeader("Tg-Chat-Id") long chatId, @Valid @RequestBody AddLinkRequest request)
    {
        return linkService.add(chatId, URI.create(request.link()));
    }

    @DeleteMapping
    @Operation(summary = "Убрать отслеживание ссылки")
    public ResponseEntity<?> deleteLink(@RequestHeader("Tg-Chat-Id") long chatId,@Valid @RequestBody RemoveLinkRequest request)
    {
        return linkService.remove(chatId,URI.create(request.link()));
    }
}
