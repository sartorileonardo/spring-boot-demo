package com.openfeign.demo.resource;

import com.openfeign.demo.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dictionary")
public class DictionaryResource {

    @Autowired
    DictionaryService bookService;

    @GetMapping("/{word}")
    public ResponseEntity getAnswer(@PathVariable("word") String word) {
        return ResponseEntity.ok(bookService.getAnswer(word));
    }

    @GetMapping("/synonyms/{word}")
    public ResponseEntity getSynonymousWord(@PathVariable("word") String word) {
        return ResponseEntity.ok(bookService.getSynonymousWord(word));
    }

    @GetMapping("/antonyms/{word}")
    public ResponseEntity getAntonymsWord(@PathVariable("word") String word) {
        return ResponseEntity.ok(bookService.getAntonymsWord(word));
    }

}
