package com.openfeign.demo.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DictionaryServiceTest {

    private static String MY_WORD = "sweet";
    private static String SYNONYM_MY_WORD = "sugary";
    private static String ANTONYM_MY_WORD = "sour";
    private static String NO_RESULT_MY_WORD = "kkkkkkk";

    @Autowired
    DictionaryService bookService;

    @Test
    public void shouldGetAnswerWithSucess(){
        assertFalse(bookService.getAnswer(MY_WORD).isEmpty());
    }

    @Test
    public void shouldGetSynonymsWithSucess(){
        assertTrue(bookService.getSynonymousWord(MY_WORD).contains(SYNONYM_MY_WORD));
    }

    @Test
    public void shouldGetAntonymsWithSucess(){
        assertTrue(bookService.getAntonymsWord(MY_WORD).contains(ANTONYM_MY_WORD));
    }

    @Test
    public void shouldReturnNoResult(){
        assertThrows(FeignException.class, () -> bookService.getAnswer(NO_RESULT_MY_WORD).stream().findFirst());
    }

}