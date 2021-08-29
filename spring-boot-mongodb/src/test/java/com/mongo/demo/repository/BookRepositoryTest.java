package com.mongo.demo.repository;

import com.mongo.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void getAllBooks(){
        assertNotNull(bookRepository.findAll());
    }

    @Test
    public void getOneBook(){
        String isbn = "612afa371d094850e801fd71";
        assertNotNull(bookRepository.findById(isbn));
    }

    @Test
    public void addBook(){
        String isbn = "612afa371d094850e801fd91";
        Book book = new Book(isbn, "Inteligência artificial", "Kai-Fu Lee", new BigDecimal("29.90"));
        Book savedBook = bookRepository.save(book);
        assertEquals(isbn, savedBook.getIsbn());
    }


    @Test
    public void updateBook(){
        String isbn = "612afa371d094850e801fd91";
        Book book = bookRepository.findById(isbn).get();
        BigDecimal newCost = new BigDecimal("75.20");
        book.setCost(newCost);
        Book bookUpdated = bookRepository.save(book);
        assertEquals(newCost, bookUpdated.getCost());
    }

    @Test
    public void deleteBook(){
        String isbn = "612afa371d094850e801fd91";
        bookRepository.deleteById(isbn);
        assertTrue(bookRepository.findById(isbn).isEmpty());
    }

}