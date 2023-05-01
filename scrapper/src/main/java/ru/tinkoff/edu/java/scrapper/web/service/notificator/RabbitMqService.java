package ru.tinkoff.edu.java.scrapper.web.service.notificator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.web.rabbitmq.service.ScrapperQueueProducer;

@RequiredArgsConstructor
public class RabbitMqService implements SendUpdatesService {
    private final ScrapperQueueProducer scrapperQueueProducer;
    @Override
    public ResponseEntity sendUpdate(LinkUpdateRequest Update) {
        scrapperQueueProducer.send(Update);
        return new ResponseEntity(HttpStatus.OK);
    }
}
