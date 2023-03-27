package ru.tinkoff.edu.java.bot.web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.web.DTO.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.web.DTO.LinkUpdateRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestController
public class BotController {

    @PostMapping(value = "/updates")
    @Operation(summary = "Отправить обновление")
    public ResponseEntity<?> linkUpdate(@Valid @RequestBody LinkUpdateRequest request) {

        return ResponseEntity.ok().build();
    }
}
