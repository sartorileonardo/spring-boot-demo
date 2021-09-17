package com.example.kafka_prod_cons;

import java.time.LocalDate;
import java.util.UUID;

public class Message {
    private String id;
    private String text;
    private LocalDate dateSend;

    public Message() {
        this.id = UUID.randomUUID().toString();
        this.dateSend = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", dateSend=" + dateSend +
                '}';
    }
}
