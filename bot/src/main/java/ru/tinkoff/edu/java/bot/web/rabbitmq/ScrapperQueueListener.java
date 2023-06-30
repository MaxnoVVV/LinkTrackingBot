package ru.tinkoff.edu.java.bot.web.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.configuration.RabbitMQConfiguration;
import ru.tinkoff.edu.java.bot.web.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.web.service.UpdateService;

@Slf4j
@RequiredArgsConstructor
@Service
@ConditionalOnBean(RabbitMQConfiguration.class)
public class ScrapperQueueListener {

  private final UpdateService updateService;

  @RabbitListener(queues = "${app.rabbitmq.queue.name}")
  public void receiver(LinkUpdateRequest update) {
    log.info("From RabbitMq: " + update.description());
    updateService.proccessUpdate(update);
  }

  @RabbitListener(queues = "${app.rabbitmq.queue.name}" + ".dlq")
  public void errorsProccessor(String s) {
    log.error("From DLQ: " + s);
  }

}
