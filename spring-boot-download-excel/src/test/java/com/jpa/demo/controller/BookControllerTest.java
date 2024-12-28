package com.jpa.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.demo.entity.Author;
import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.PublishingCompany;
import com.jpa.demo.exception.ResourceNotFoundException;
import com.jpa.demo.repository.AuthorRepository;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.repository.PublishingCompanyRepository;
import com.jpa.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService service;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private PublishingCompanyRepository publishingCompanyRepository;

    private Book book;
    private List<Author> authors;
    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        // Given
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(8L)
                .authors(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @Test
    @DisplayName("Should return a list of saved books")
    void getAllBooks() throws Exception {
        // Given
        Book otherBook = Book.builder()
                .id(9L)
                .authors(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("AnyBook")
                .cost(new BigDecimal("10.50"))
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(otherBook);

        given(service.findAll()).willReturn(books);

        // When
        ResultActions response = mockMvc.perform(get("/books"));

        // Then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @Test
    @DisplayName("Should return an existing book")
    public void getOneBook() throws Exception {
        // Given
        long bookId = 10L;
        given(service.findById(bookId)).willReturn(Optional.ofNullable(book));

        // When
        ResultActions response = mockMvc.perform(get("/books/{id}", bookId));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())));
    }

    @Test
    @DisplayName("Should return exception when book not found")
    public void getOneBookWhenBookNotFound() throws Exception {
        // Given
        long bookId = 10L;
        given(service.findById(bookId)).willThrow(ResourceNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(get("/v1/books/{id}", bookId));

        // Then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should save a new book successfully")
    void addBook() throws Exception {
        // Given
        given(service.save(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(book.getName())));
    }

    @Test
    @DisplayName("Should update an existing book successfully")
    void updateBook() throws Exception {
        // Given
        long bookId = 10L;
        Book updatedBook = Book.builder()
                .id(bookId)
                .authors(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("updatedBook")
                .cost(new BigDecimal("15.50"))
                .build();
        given(service.findById(bookId)).willReturn(Optional.ofNullable(book));
        given(service.update(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedBook.getName())));
    }

    @Test
    @DisplayName("Should return exception when trying to update a non-existing book")
    void updateBookWhenNotExistingBook() throws Exception {
        // Given
        long bookId = 10L;
        Book updatedBook = Book.builder()
                .id(bookId)
                .authors(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("updatedBook")
                .cost(new BigDecimal("15.50"))
                .build();
        given(service.findById(bookId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(1));

        // When
        ResultActions response = mockMvc.perform(put("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));

        // Then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() throws Exception {
        // Given
        long bookId = 10L;
        willDoNothing().given(service).deleteById(bookId);

        // When
        ResultActions response = mockMvc.perform(delete("/books/{id}", bookId));

        // Then
        response.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should generate and return a books report")
    void getBooksReport() throws Exception {
        // Given
        byte[] reportContent = "Report content".getBytes(); // Simulated content, can be a PDF/CSV file, etc.
        given(service.getBooksReport()).willReturn(new ResponseEntity<>(reportContent, HttpStatus.OK));

        // When
        ResultActions response = mockMvc.perform(get("/books/report")
                .accept(MediaType.APPLICATION_OCTET_STREAM)); // Assuming the report is EXCEL

        // Then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(content().bytes(reportContent));
    }
}
