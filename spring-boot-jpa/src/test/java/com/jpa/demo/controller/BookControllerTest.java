package com.jpa.demo.controller;

import com.jpa.demo.entity.Author;
import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.PublishingCompany;
import com.jpa.demo.repository.AuthorRepository;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.repository.PublishingCompanyRepository;
import com.jpa.demo.service.BookService;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        //Given
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

    @Test
    @DisplayName("Should return a list from saved books")
    void getAllBooks() throws Exception {
        //Given
        Book otherBook = Book.builder()
                .id(9L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("AnyBook")
                .cost(new BigDecimal("10.50"))
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(otherBook);

        given(service.findAll()).willReturn(books);

        //When
        ResultActions response = mockMvc.perform(get("/v1/books"));

        //Then
        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @Test
    @DisplayName("Should return a existing book")
    public void getOneBook() throws Exception {
        //Given
        long bookId = 10L;
        given(service.findById(bookId)).willReturn(Optional.ofNullable(book));

        //When
        ResultActions response = mockMvc.perform(get("/v1/books/{id}", bookId));

        //Then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())));
    }

    @Test
    @DisplayName("Should save a new book with sucess")
    void addBook() throws Exception {
        //Given
        given(service.save(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //When
        ResultActions response = mockMvc.perform(post("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)));

        //Then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(book.getName())));
    }


    @Test
    @DisplayName("Should update existing book with sucess")
    void updateBook() {
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() {
    }

}