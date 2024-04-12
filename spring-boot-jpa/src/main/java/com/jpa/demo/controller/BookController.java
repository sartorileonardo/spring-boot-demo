package com.jpa.demo.controller;

import com.jpa.demo.entity.Book;
import com.jpa.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.of(Optional.of(savedBook));
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books) {
        bookService.saveAll(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("id") Long id) {
        bookService.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks() {
        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Long id) {
        return bookService.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

}
