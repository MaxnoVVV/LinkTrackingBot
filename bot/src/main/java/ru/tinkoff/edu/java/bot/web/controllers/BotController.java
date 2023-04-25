package ru.tinkoff.edu.java.bot.web.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.web.bot.Bot;
import ru.tinkoff.edu.java.bot.web.dto.LinkUpdateRequest;
@Slf4j
@RestController
public class BotController {
    @Autowired
    Bot bot;

    @PostMapping(value = "/updates")
    @Operation(summary = "Отправить обновление")
    public ResponseEntity<?> linkUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        log.info("got update");
        bot.receiveUpdate(request.thChatIds()[0],request.url(),request.description());
        return ResponseEntity.ok().build();
    }
}
