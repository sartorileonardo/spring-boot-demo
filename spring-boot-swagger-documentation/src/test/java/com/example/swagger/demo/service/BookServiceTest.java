package com.example.swagger.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void getBooks() {
        assertNotNull(bookService.getBooks());
        assertFalse(bookService.getBooks().isEmpty());
    }
}