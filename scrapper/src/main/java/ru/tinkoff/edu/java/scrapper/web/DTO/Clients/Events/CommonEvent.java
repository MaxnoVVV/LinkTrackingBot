package ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Events;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Actor;
import ru.tinkoff.edu.java.scrapper.web.DTO.Clients.Payloads.Payload;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommonEvent {
    String id;
    String type;
    Actor actor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date created_at;
    JsonNode payload;
    Object org;

}
