package ru.tinkoff.edu.java.scrapper.web.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.web.DTO.controllers.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.DTO.controllers.LinkResponse;
import ru.tinkoff.edu.java.scrapper.web.DTO.controllers.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.web.DTO.controllers.RemoveLinkRequest;

@RestController
@RequestMapping(value = "/links")
public class LinksControllers {
    @GetMapping
    @Operation(summary = "Получить все отслеживаемые ссылки")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") int chatId)
    {
        return new ResponseEntity<>(new ListLinksResponse(new LinkResponse[]{new LinkResponse(1, "string")}), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Добавить отслеживание ссылки")
    public ResponseEntity<LinkResponse> postLink(@RequestHeader("Tg-Chat-Id") int chatId, @Valid @RequestBody AddLinkRequest request)
    {
        return new ResponseEntity<>(new LinkResponse(1,"link"),HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Убрать отслеживание ссылки")
    public ResponseEntity<LinkResponse> deleteLink(@RequestHeader("Tg-Chat-Id") int chatId,@Valid @RequestBody RemoveLinkRequest request)
    {
        return new ResponseEntity<>(new LinkResponse(1,"link"),HttpStatus.OK);
    }
}
