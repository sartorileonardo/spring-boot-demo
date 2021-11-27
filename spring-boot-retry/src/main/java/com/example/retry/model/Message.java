package com.example.retry.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {
    private String id;
    private String text;
    private String author;
    private LocalDateTime sendDate;
}
