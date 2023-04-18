package ru.tinkoff.edu.java.bot.web.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.web.dto.controllers.RemoveLinkRequest;

public class ScrapperClient {
    private static String baseurl;
    private WebClient client;

    public ScrapperClient()
    {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))
                .baseUrl(baseurl)// property
                .build();
        baseurl = "localhost:8080";
    }
    public ScrapperClient(String url)
    {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))
                .baseUrl(baseurl)// property
                .build();
        baseurl = url;
    }

    public ListLinksResponse getLinks(long chatId)
    {
        return client
                .get()
                .uri("/links")
                .header("Tg-Chat-Id",Long.toString(chatId))
                .retrieve().bodyToMono(ListLinksResponse.class).block();
    }

    public ResponseEntity<?> addLink(long chatId, AddLinkRequest request)
    {
        return client
                .post()
                .uri("/links")
                .header("Tg-Chat-Id",Long.toString(chatId))
                .retrieve().bodyToMono(ResponseEntity.class).block();
    }

    public ResponseEntity<?> deleteLink(long chatId, RemoveLinkRequest request)
    {
        return client
                .delete()
                .uri("/links")
                .header("Tg-Chat-Id",Long.toString(chatId))
                .retrieve().bodyToMono(ResponseEntity.class).block();
    }

    public ResponseEntity<?> registerChat(long ChatId)
    {
        return client
                .post()
                .uri(String.format("/tg-chat/%d",ChatId))
                .retrieve()
                .bodyToMono(ResponseEntity.class).block();
    }

    public ResponseEntity<?> deleteChat(long ChatId)
    {
        return client
                .delete()
                .uri(String.format("/tg-chat/%d",ChatId))
                .retrieve()
                .bodyToMono(ResponseEntity.class).block();
    }

    
}
