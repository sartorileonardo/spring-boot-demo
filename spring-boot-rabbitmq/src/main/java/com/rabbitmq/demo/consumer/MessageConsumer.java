package com.rabbitmq.demo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RabbitListener(queues = "messages")
public class MessageConsumer {

    @RabbitListener(queues = {"messages"})
    public void receive(String message){
        log.info("Received: " + message);
    }
}
