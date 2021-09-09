package com.example.crud.jdbc.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {
    private Integer id;
    private String text;
}
