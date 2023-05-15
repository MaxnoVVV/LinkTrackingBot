package ru.tinkoff.edu.java.bot.web.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.web.dto.clients.*;


@Component
@Slf4j
public class ScrapperClient {

  private static final ObjectMapper mapper = new ObjectMapper().configure(
      DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  private static String baseurl;
  private WebClient client;

  public ScrapperClient() {
    log.info("client for scrapper with default url created");
    client = WebClient.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1000000))// property
        .build();
    baseurl = "http://localhost:8081";
  }

  public ScrapperClient(String url) {
    log.info("client for scrapper with ### url created");
    client = WebClient.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1000000))// property
        .build();
    baseurl = url;
  }

  public ResponseEntity getLinks(long chatId) {
    log.info("client getLinks method executing");

    var response = client.get().uri(baseurl + "/links").header("Tg-Chat-Id", Long.toString(chatId))
        .retrieve().onStatus(status -> status.value() == 400, resp -> Mono.empty())
        .toEntity(String.class).block();
    if (response.getStatusCode().is4xxClientError()) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      try {
        return new ResponseEntity(
            mapper.readValue(response.getBody().toString(), ListLinksResponse.class),
            HttpStatus.OK);
      } catch (Exception e) {
        log.error(e.toString());
        log.error(e.getMessage());
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

  }

  public ResponseEntity<?> addLink(long chatId, AddLinkRequest request) {

    log.info("client addLinks method executing");
    ResponseEntity tempres = client.post().uri(baseurl + "/links")
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(request)).header("Tg-Chat-Id", Long.toString(chatId))
        .retrieve().onStatus(status -> status.is4xxClientError(), resp -> Mono.empty())
        .toEntity(String.class).block();

    log.info(tempres.getStatusCode().toString());
    log.info(tempres.toString());
    log.info(tempres.getBody().toString());
    try {
      if (tempres.getStatusCode().is4xxClientError()) {
        return new ResponseEntity<>(
            mapper.readValue(tempres.getBody().toString(), ApiErrorResponse.class),
            HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity(
            mapper.readValue(tempres.getBody().toString(), LinkResponse.class), HttpStatus.OK);
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }


  }

  public ResponseEntity<?> deleteLink(long chatId, RemoveLinkRequest request) {
    log.info("client deleteLinks method executing");
    ResponseEntity result = client.method(HttpMethod.DELETE).uri(baseurl + "/links")
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(request)).header("Tg-Chat-Id", Long.toString(chatId))
        .retrieve().onStatus(status -> status.value() == 400, resp -> Mono.empty())
        .toEntity(String.class).block();

    try {
      if (result.getStatusCode().is4xxClientError()) {
        return new ResponseEntity<>(
            mapper.readValue(result.getBody().toString(), ApiErrorResponse.class),
            HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>(
            mapper.readValue(result.getBody().toString(), LinkResponse.class), HttpStatus.OK);
      }
    } catch (Exception e) {
      log.error(e.toString());
      log.error(e.getMessage());
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


  }

  public ResponseEntity<?> registerChat(long ChatId) {
    log.info("client registerChat method executing");
    log.info(baseurl + "/tg-chat/" + Long.toString(ChatId));
    ResponseEntity response = client.post().uri(baseurl + "/tg-chat/" + Long.toString(ChatId))
        .retrieve().onStatus(status -> status.value() == 400, resp -> Mono.empty())
        .toEntity(String.class).block();
    return response;
  }

  public ResponseEntity<?> deleteChat(long ChatId) {
    log.info("client deletechatwhithidonlygetLinks method executing");
    return client.delete().uri(baseurl + "/tg-chat/%d" + ChatId).retrieve()
        .onStatus(status -> status.value() == 400, resp -> Mono.empty())
        .bodyToMono(ResponseEntity.class).block();
  }


}
