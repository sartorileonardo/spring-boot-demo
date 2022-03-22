package com.r2dbc.demo.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    private static final Long ID_BOOK = 1L;
    private static final String TITLE_BOOK = "abc";

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void add() throws Exception {
        String bodyBook = "{\n" +
                "    \"title\": \"ABC\",\n" +
                "    \"author\": \"CDE\",\n" +
                "    \"price\": 500.00\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/book")
                        .content(bodyBook)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/" + ID_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    void findByTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/findbytitle/" + TITLE_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    void update() throws Exception {
        String bodyBook = "{\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"XPTO\",\n" +
                "    \"author\": \"testAuthor\",\n" +
                "    \"price\": 40.00\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/book/" + ID_BOOK)
                        .content(bodyBook)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/book/" + ID_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}