package ru.tinkoff.edu.java.scrapper.web.clients;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;

public class BotClient {

    private WebClient client;
    StringBuffer uri;

    public BotClient() {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = new StringBuffer("localhost:8080");
    }

    public BotClient(String url) {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = new StringBuffer(url);
    }

    public ResponseEntity<?> sendUpdate(LinkUpdateRequest update)
    {
        return client.post()
                .uri("/updates")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(update))
                .retrieve().bodyToMono(ResponseEntity.class).block();
    }

}
