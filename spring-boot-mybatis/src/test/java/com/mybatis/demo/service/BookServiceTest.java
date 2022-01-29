package com.mybatis.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mybatis.demo.entity.Book;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    public void getAllBooks(){
        assertNotNull(bookService.findAll());
    }

    @Test
    public void getOneBook(){
        assertNotNull(bookService.findOne(1L));
    }

    @Test
    public void addBook(){
        Long bookId = 4L;
        Book book = Book.builder()
            .id(bookId)
            .name("The Pragmatic Programmer")
            .isbn("989328")
            .price(BigDecimal.valueOf(90.00))
            .author("Kent Beck")
            .build();
        bookService.save(book);
        assertTrue(bookService.findOne(bookId).isPresent());
    }


    @Test
    public void updateBook(){
        Long bookId = 3L;
        String newIsbn = "989328";
        Book book = Book.builder()
            .id(bookId)
            .name("The Pragmatic Programmer")
            .isbn(newIsbn)
            .price(BigDecimal.valueOf(95.00))
            .author("Kent Beck")
            .build();
        bookService.save(book);
        assertEquals(newIsbn, bookService.findOne(bookId).get().getIsbn());
    }

    @Test
    public void deleteBook(){
        Long bookId = 3L;
        bookService.delete(bookId);
        assertTrue(bookService.findOne(bookId).isEmpty());
    }

}