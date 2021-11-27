package com.example.retry.controller;

import com.example.retry.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{keyWord}")
    public ResponseEntity getMessage(@PathVariable("keyWord") String keyWord){
        log.info("Loading message...");
        return messageService.getMessage(keyWord).isPresent() ? ResponseEntity.ok(messageService.getMessage(keyWord).get()) : ResponseEntity.notFound().build();
    }
}
