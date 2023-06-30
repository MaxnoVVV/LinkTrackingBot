package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.web.dto.LinkUpdateRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class RabbitMQConfiguration {

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
  public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
    Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
    jsonMessageConverter.setClassMapper(classMapper);
    return jsonMessageConverter;
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
  public ClassMapper classMapper() {
    Map<String, Class<?>> mappings = new HashMap<>();
    mappings.put("ru.tinkoff.edu.java.scrapper.web.dto.forclient.dto.LinkUpdateRequest",
        LinkUpdateRequest.class);
    DefaultClassMapper classMapper = new DefaultClassMapper();
    classMapper.setTrustedPackages("ru.tinkoff.edu.java.bot.web.dto.*");
    classMapper.setIdClassMapping(mappings);
    return classMapper;
  }

  @Bean
  public Queue dlq(ApplicationConfig config) {
    return new Queue(config.rabbitmq().queue().name() + ".dlq");
  }

  @Bean
  public DirectExchange dlx(ApplicationConfig config) {
    return new DirectExchange(config.rabbitmq().exchange().name() + ".dlq");
  }

  @Bean
  public Binding dlqBinding(ApplicationConfig config) {
    return BindingBuilder.bind(dlq(config)).to(dlx(config)).with(routingKey(config));
  }
}
