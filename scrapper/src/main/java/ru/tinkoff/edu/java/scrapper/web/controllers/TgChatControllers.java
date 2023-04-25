package ru.tinkoff.edu.java.scrapper.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;

@RestController
@RequestMapping(value = "tg-chat/{id}")
@Slf4j
public class TgChatControllers {
    @Autowired
    TgChatService service;
    @PostMapping
    @Operation(summary = "Зарегистрировать чат")
    public ResponseEntity<?> registerChat(@PathVariable long id)
    {
        log.info("registering" + id);
        return service.register(id);
    }

    @DeleteMapping
    @Operation(summary = "удалить чат")
    public ResponseEntity<?> deleteChat(@PathVariable long id)
    {
        log.info("unregistering" + id);
        return service.unregister(id);
    }
}

