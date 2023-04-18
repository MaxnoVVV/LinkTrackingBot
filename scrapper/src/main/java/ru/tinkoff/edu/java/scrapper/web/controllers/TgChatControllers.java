package ru.tinkoff.edu.java.scrapper.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "tg-chat/{id}")
public class TgChatControllers {
    @PostMapping
    @Operation(summary = "Зарегистрировать чат")
    public ResponseEntity<?> registerChat(@PathVariable int id)
    {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "удалить чат")
    public ResponseEntity<?> deleteChat(@PathVariable int id)
    {
        return ResponseEntity.ok().build();
    }
}

