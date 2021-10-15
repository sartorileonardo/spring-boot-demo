package com.example.jmh.demo.service;

import com.example.jmh.demo.model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MessageService {

    private final Integer MIN_MSG_ITERATIONS = 0;
    private final Integer MAX_MSG_ITERATIONS = 10;

    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>();
        IntStream.range(MIN_MSG_ITERATIONS, MAX_MSG_ITERATIONS).forEach( i -> {
            messages.add(new Message());
        });
        return messages;
    }

}
