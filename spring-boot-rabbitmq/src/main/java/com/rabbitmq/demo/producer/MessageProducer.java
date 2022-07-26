package com.rabbitmq.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Random;

@Slf4j
public class MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue queue;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send(){
        List<String> randomNames = List.of("Homer Simpson", "Bruce Wayne", "Bob Esponja");
        String message = "Hello Sr. " + randomNames.get(new Random().nextInt(randomNames.size()));
        rabbitTemplate.convertAndSend(queue.getName(), message);
        log.info("Send: " + message);
    }
}
