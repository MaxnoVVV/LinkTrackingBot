package ru.tinkoff.edu.java.scrapper.web.clients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.events.CommonEvent;

import java.util.List;


public class GitHubWebClient {
    private WebClient client;
    StringBuffer uri;

    public GitHubWebClient() {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = new StringBuffer("https://api.github.com/repos/");
    }

    public GitHubWebClient(String url) {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000))// property
                .build();
        uri = new StringBuffer(url);
    }

    @SneakyThrows
    public List<CommonEvent> getInfo(String owner, String repo) {

        uri.append(owner + "/");
        uri.append(repo + "/");
        uri.append("events");

        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String temp = client
                .get()
                .uri(uri.toString())
                .retrieve()
                .bodyToMono(String.class).block();

        return mapper.readValue(temp, new TypeReference<List<CommonEvent>>() {
        });
    }
}
