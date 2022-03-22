package com.r2dbc.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Value
@Builder
@ToString
public class Book {
    @Id
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
}
