package com.example.jmh.demo.model;

import ch.qos.logback.core.util.StringCollectionUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Message {
    private Integer id;
    private String text;
    private LocalDateTime received;

    public Message() {
        this.id = new Random().nextInt();
        this.text = UUID.randomUUID().toString();
        this.received = LocalDateTime.now();
    }
}
