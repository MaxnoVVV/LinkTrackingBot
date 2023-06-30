package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.web.rabbitmq.service.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.RabbitMqService;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.SendUpdatesService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "use-queue", havingValue = "true")
public class RabbitMQConfiguration {

@Bean
public ScrapperQueueProducer scrapperQueueProducer(RabbitTemplate rabbitTemplate,ApplicationConfig config)
{
  return new ScrapperQueueProducer(rabbitTemplate,config.rabbitmq().exchange().name(),config.rabbitmq().binding().routingKey());
}
  @Bean
  public String routingKey(ApplicationConfig config) {
    return config.rabbitmq().binding().routingKey();
  }

  @Bean
  public Queue queue(ApplicationConfig config) {
    Queue queue = new Queue(config.rabbitmq().queue().name());
    queue.addArgument("x-dead-letter-exchange", config.rabbitmq().exchange().name() + ".dlq");
    return queue;
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }


  @Bean
  public DirectExchange exchange(ApplicationConfig config) {
    return new DirectExchange(config.rabbitmq().exchange().name());
  }

  @Bean
  public Binding binding(ApplicationConfig config, Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(config.rabbitmq().binding().routingKey());
  }

  @Bean
  public RabbitTemplate rabbitTemplate(
      org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public SendUpdatesService sendUpdatesService(ScrapperQueueProducer scrapperQueueProducer) {
    return new RabbitMqService(scrapperQueueProducer);
  }


}
