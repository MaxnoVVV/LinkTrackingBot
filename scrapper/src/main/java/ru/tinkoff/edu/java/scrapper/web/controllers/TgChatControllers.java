package ru.tinkoff.edu.java.scrapper.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.web.services.TgChatService;

@RestController
@RequestMapping(value = "tg-chat/{id}")
public class TgChatControllers {
    @Autowired
    TgChatService service;
    @PostMapping
    @Operation(summary = "Зарегистрировать чат")
    public ResponseEntity<?> registerChat(@PathVariable int id)
    {
        return service.register(id);
    }

    @DeleteMapping
    @Operation(summary = "удалить чат")
    public ResponseEntity<?> deleteChat(@PathVariable int id)
    {
        return service.unregister(id);
    }
}

