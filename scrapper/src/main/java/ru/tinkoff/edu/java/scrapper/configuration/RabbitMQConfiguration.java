package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.web.rabbitmq.service.ScrapperQueueProducer;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.RabbitMqService;
import ru.tinkoff.edu.java.scrapper.web.service.notificator.SendUpdatesService;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public String queueName(ApplicationConfig config)
    {
        return config.rabbitmq().queue().name();
    }

    @Bean
    public String exchangeName(ApplicationConfig config)
    {
        return config.rabbitmq().exchange().name();
    }

    @Bean
    public String routingKey(ApplicationConfig config){return config.rabbitmq().binding().routingKey();}

    @Bean
    public Queue queue(ApplicationConfig config)
    {
        return new Queue(queueName(config));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public DirectExchange exchange(ApplicationConfig config)
    {
        return new DirectExchange(config.rabbitmq().exchange().name());
    }

    @Bean
    public Binding binding(ApplicationConfig config, Queue queue, DirectExchange exchange)
    {
        return BindingBuilder.bind(queue).to(exchange).with(config.rabbitmq().binding().routingKey());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SendUpdatesService sendUpdatesService(ScrapperQueueProducer scrapperQueueProducer)
    {
        return new RabbitMqService(scrapperQueueProducer);
    }

}
