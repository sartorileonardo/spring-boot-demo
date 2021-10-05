package com.example.swagger.demo.service;

import com.example.swagger.demo.model.Book;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
public class BookService {
    public List<Book> getBooks(){
        return List.of(
                new Book(UUID.randomUUID().toString(), "Clean Arquitecture", "Robert Martin", LocalDateTime.now(), new BigDecimal("150.50").setScale(2, RoundingMode.HALF_DOWN)),
                new Book(UUID.randomUUID().toString(), "Mastering Microservices with Java", "Sourabh Sharma", LocalDateTime.now(), new BigDecimal("375.25").setScale(2, RoundingMode.HALF_DOWN)),
                new Book(UUID.randomUUID().toString(), "Spring in Action", "Craig Walls", LocalDateTime.now(), new BigDecimal("311.75").setScale(2, RoundingMode.HALF_DOWN))
        );
    }
}
