package ru.tinkoff.edu.java.scrapper.web.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private boolean is_accepted;
    private long creation_date;
    private String answer_id;
    private String question_id;
    private String body;
    private Owner owner;

    public OffsetDateTime getCreation_date() {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(creation_date),ZoneId.systemDefault());
    }
}

