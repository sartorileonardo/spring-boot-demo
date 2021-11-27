package com.example.retry.service;

import com.example.retry.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
@SpringBootTest
class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Test
    void shouldReturnedNewMessage(){
        Optional<Message> msg = messageService.getMessage("hello");
        Assertions.assertTrue(msg.isPresent());
    }
}