package ru.tinkoff.edu.java.bot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.web.bot.Bot;
import ru.tinkoff.edu.java.bot.web.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.web.service.UpdateService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController {
    private final UpdateService updateService;

    @PostMapping(value = "/updates")
    @Operation(summary = "Отправить обновление")
    public ResponseEntity<?> linkUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        log.info("got update");
        updateService.proccessUpdate(request);
        return ResponseEntity.ok().build();
    }
}
