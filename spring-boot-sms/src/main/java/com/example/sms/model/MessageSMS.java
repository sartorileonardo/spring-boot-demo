package com.example.sms.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MessageSMS {
    private String id;
    private String text;
    private LocalDateTime sendDate;

    public MessageSMS() {
        this.id = UUID.randomUUID().toString();
        this.sendDate = LocalDateTime.now();
    }
}
