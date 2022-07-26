package com.rabbitmq.demo.config;

import com.rabbitmq.demo.consumer.MessageConsumer;
import com.rabbitmq.demo.producer.MessageProducer;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfig {

    private final String QUEUE_NAME = "messages";

    @Bean
    public Queue booksQueue(){
        return new Queue(QUEUE_NAME, true);
    }

    @Profile("consumer")
    @Bean
    public MessageConsumer consumer(){
        return new MessageConsumer();
    }

    @Profile("producer")
    @Bean
    public MessageProducer producer(){
        return new MessageProducer();
    }

}
