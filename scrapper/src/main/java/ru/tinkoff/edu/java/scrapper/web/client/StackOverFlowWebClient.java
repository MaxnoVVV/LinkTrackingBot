package ru.tinkoff.edu.java.scrapper.web.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.Item;
import ru.tinkoff.edu.java.scrapper.web.dto.clients.StackOverFlowResponse;

@ToString
public class StackOverFlowWebClient {
    private final WebClient client;

    private static final String baseurl =
            "https://api.stackexchange.com/2.3/";
    private static final String uri =
            "questions/%s/answers?site=stackoverflow&filter=%s";
    private static final String filter =
            "pe4ZET_zc8ymi";

    private static final  ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS,true);

    public StackOverFlowWebClient(String url) {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs()
                        .maxInMemorySize(1000000)).baseUrl(url) // property
                .build();
    }
    public StackOverFlowWebClient() {
        client = WebClient
                .builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1000000))
                .baseUrl(baseurl)
                .build();
    }

    @SneakyThrows
    public StackOverFlowResponse getAnswers(String id) {
        String tempuri = String.format(uri, id, filter);
        ResponseEntity tempJson = client
                .get()
                .uri(tempuri)
                .retrieve()
                .onStatus(httpStatusCode -> httpStatusCode.is4xxClientError(), resp -> Mono.empty())
                .onStatus(httpStatusCode -> httpStatusCode.is5xxServerError(), resp -> Mono.empty())
                .toEntity(String.class)
                .block();
        if (tempJson.getStatusCode().is2xxSuccessful()) {
            return mapper.readValue(tempJson.getBody().toString(), StackOverFlowResponse.class);
        }
        else
        {
            return new StackOverFlowResponse(new Item[]{});
        }
    }

}
