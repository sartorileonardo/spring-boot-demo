package com.itext.demo.repository;

import com.itext.demo.entity.Author;
import com.itext.demo.entity.Book;
import com.itext.demo.entity.PublishingCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;

    @Test
    public void getAllBooks(){
        assertNotNull(bookRepository.findAll());
    }

    @Test
    public void getOneBook(){
        Integer bookId = 1;
        assertNotNull(bookRepository.findById(bookId));
    }

    @Test
    public void addBook(){
        PublishingCompany publishingCompany = publishingCompanyRepository.getById(1);
        List<Author> authors = authorRepository.findAllById(List.of(1, 2));
        Integer bookId = 5;
        Book book = new Book(bookId, UUID.randomUUID().toString(), "Inteligência artificial", new BigDecimal("29.90"), authors, publishingCompany);
        Book savedBook = bookRepository.save(book);
        assertEquals(bookId, savedBook.getId());
    }


    @Test
    public void updateBook(){
        Integer bookId = 4;
        Book book = bookRepository.findById(bookId).get();
        BigDecimal newCost = new BigDecimal("75.20");
        book.setCost(newCost);
        Book bookUpdated = bookRepository.save(book);
        assertEquals(newCost, bookUpdated.getCost());
    }

    @Test
    public void deleteBook(){
        Integer bookId = 4;
        bookRepository.deleteById(bookId);
        assertTrue(bookRepository.findById(bookId).isEmpty());
    }

}