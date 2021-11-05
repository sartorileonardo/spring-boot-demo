package com.mongo.demo.resource;

import com.mongo.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookResourceTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void shouldReturnAllBooks(){
        List<Book> books = getAllBooks();
        assertFalse(books.isEmpty());
    }

    @Test
    public void shouldAddNewBook(){
        Book book = new Book("Core Java Ultimate", "Kathy Sierra", new BigDecimal("2200.25").setScale(2));

        webClient
                .post()
                .uri("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isOk();

        Book bookSaved = getAllBooks().stream().filter(b -> b.getName().equalsIgnoreCase(book.getName())).findFirst().get();
        assertEquals(book.getName(), bookSaved.getName());

    }

    private List<Book> getAllBooks() {
        return webClient
                .get()
                .uri("/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Book.class)
                .getResponseBody()
                .collectList()
                .block();
    }

}