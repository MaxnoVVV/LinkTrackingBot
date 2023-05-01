package ru.tinkoff.edu.java.scrapper.web.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;
    public void send(LinkUpdateRequest update)
    {
        rabbitTemplate.convertAndSend(exchangeName,routingKey,update);
    }

}
