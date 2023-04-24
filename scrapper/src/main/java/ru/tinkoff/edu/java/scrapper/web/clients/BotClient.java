package ru.tinkoff.edu.java.scrapper.web.clients;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;

@Component("botClient")
public class BotClient {

    private WebClient client;
    StringBuffer uri;

    public BotClient() {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = new StringBuffer("http://localhost:8080");
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
                .uri(uri.toString() + "/updates")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(update))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),resp -> Mono.empty())
                .onStatus(status -> status.is5xxServerError(),resp -> Mono.empty())
                .toEntity(String.class)
                .block();
    }

}
