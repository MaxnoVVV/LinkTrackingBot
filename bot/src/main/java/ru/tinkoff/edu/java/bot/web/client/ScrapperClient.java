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
    client = WebClient.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1000000))
        .build();
    baseurl = "http://localhost:8081";
    log.info("client for scrapper with default url created");
  }

  public ScrapperClient(String url) {
    client = WebClient.builder()
        .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(1000000))
        .build();
    baseurl = url;
    log.info(String.format("client for scrapper with %s url created", url));
  }

  public ResponseEntity getLinks(long chatId) {
    log.info("client getLinks method executing");
    try {
      var response = client.get().uri(baseurl + "/links")
          .header("Tg-Chat-Id", Long.toString(chatId))
          .retrieve().onStatus(status -> status.value() == 400, resp -> Mono.empty())
          .toEntity(String.class).block();
      if (response.getStatusCode().is4xxClientError()) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity(
            mapper.readValue(response.getBody().toString(), ListLinksResponse.class),
            HttpStatus.OK);
      }
    } catch (Exception e) {
      log.warn("Exception in getLinks client method");
      log.error(e.toString());
      log.error(e.getMessage());
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }


  }

  public ResponseEntity<?> addLink(long chatId, AddLinkRequest request) {

    log.info("client addLinks method executing");
    try {
      ResponseEntity tempres = client.post().uri(baseurl + "/links")
          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromObject(request)).header("Tg-Chat-Id", Long.toString(chatId))
          .retrieve().onStatus(status -> status.is4xxClientError(), resp -> Mono.empty())
          .toEntity(String.class).block();
      if (tempres.getStatusCode().is4xxClientError()) {
        return new ResponseEntity<>(
            mapper.readValue(tempres.getBody().toString(), ApiErrorResponse.class),
            HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity(
            mapper.readValue(tempres.getBody().toString(), LinkResponse.class), HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  public ResponseEntity<?> deleteLink(long chatId, RemoveLinkRequest request) {
    log.info("client deleteLinks method executing");
    try {
      ResponseEntity result = client.method(HttpMethod.DELETE).uri(baseurl + "/links")
          .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromObject(request)).header("Tg-Chat-Id", Long.toString(chatId))
          .retrieve().onStatus(status -> status.value() == 400, resp -> Mono.empty())
          .toEntity(String.class).block();
      if (result.getStatusCode().is4xxClientError()) {
        return new ResponseEntity<>(
            mapper.readValue(result.getBody().toString(), ApiErrorResponse.class),
            HttpStatus.BAD_REQUEST);
      } else {
        return new ResponseEntity<>(
            mapper.readValue(result.getBody().toString(), LinkResponse.class), HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
