package com.mongo.demo.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String isbn;

    @NonNull
    private String name;

    @NonNull
    private String author;

    @NonNull
    private BigDecimal cost;

}
