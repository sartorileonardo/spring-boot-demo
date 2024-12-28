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
@RequestMapping("/books")
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

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.update(book));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks() {
        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.of(bookService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> getBooksReport() {
        return bookService.getBooksReport();
    }

}
