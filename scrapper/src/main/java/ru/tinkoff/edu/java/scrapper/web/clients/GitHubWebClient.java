package ru.tinkoff.edu.java.scrapper.web.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.events.CommonEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GitHubWebClient {
    private WebClient client;
    String uri;

    public GitHubWebClient() {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = "https://api.github.com/repos/";
    }

    public GitHubWebClient(String url) {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = url;
    }

    @SneakyThrows
    public List<CommonEvent> getInfo(String owner, String repo) {

        StringBuffer tempuri = new StringBuffer(uri);
        tempuri.append(owner + "/");
        tempuri.append(repo + "/");
        tempuri.append("events");

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ResponseEntity temp = client
                .get()
                .uri(tempuri.toString())
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError(),resp -> Mono.empty())
                .onStatus(httpStatusCode -> httpStatusCode.is5xxServerError(),resp -> Mono.empty())
                .toEntity(String.class).block();

        if(temp.getStatusCode().is2xxSuccessful())
        {
            return mapper.readValue(temp.getBody().toString(), new TypeReference<List<CommonEvent>>() {
            });
        }
        else
        {
            return new ArrayList<CommonEvent>();
        }


    }
}
