package ru.tinkoff.edu.java.scrapper.web.clients;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.web.reactive.function.client.WebClient;
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
        String tempuri = String.format(uri,id,filter);
        String tempJson = client.get().uri(tempuri).retrieve().bodyToMono(String.class).block();

        return mapper.readValue(tempJson, StackOverFlowResponse.class);
        }
}
