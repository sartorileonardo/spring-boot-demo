package com.example.redis.config;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message){
        LOGGER.info("Received message: " + message + " at " + LocalDateTime.now());
        counter.incrementAndGet();
    }

    public int getCount(){
        return counter.get();
    }

}
