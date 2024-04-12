package com.jpa.demo.repository;

import com.jpa.demo.entity.Author;
import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.PublishingCompany;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    private List<Author> authors;

    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        //Given
        bookRepository.deleteAll();
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(8L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list from saved books")
    void getAllBooks() {
        //Given
        bookRepository.save(book);

        //When
        List<Book> books = bookRepository.findAll();

        //Then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Should return a existing book")
    public void getOneBook() {
        //Given & When
        Book savedBook = bookRepository.save(book);

        //Then
        assertNotNull(savedBook);
        assertEquals(book.getId(), savedBook.getId());
    }

    @Test
    @DisplayName("Should save a new book with sucess")
    void addBook() {
        //Then
        Book savedBook = bookRepository.save(book);

        //When
        assertNotNull(savedBook);
    }


    @Test
    @DisplayName("Should update existing book with sucess")
    void updateBook() {
        //Given
        Book savedBook = bookRepository.save(book);

        //When
        String newBookName = "NewBookName";
        savedBook.setName(newBookName);
        Book bookUpdated = bookRepository.save(savedBook);

        //Then
        assertNotNull(bookUpdated);
        assertEquals(newBookName, bookUpdated.getName());
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() {
        //Given
        Book savedBook = bookRepository.save(book);

        //When
        bookRepository.deleteById(savedBook.getId());

        //Then
        assertTrue(bookRepository.findById(savedBook.getId()).isEmpty());
    }

}