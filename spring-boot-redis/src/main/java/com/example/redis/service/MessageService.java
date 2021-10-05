package com.example.redis.service;


import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ReceiverService {

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message){
        log.info("Received message: " + message + " at " + LocalDateTime.now());
        counter.incrementAndGet();
    }

    public int getCount(){
        return counter.get();
    }

}
