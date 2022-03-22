package com.r2dbc.demo.controller;

import com.r2dbc.demo.entity.Book;
import com.r2dbc.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Flux<Book> findAll(){
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Book>> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/findbytitle/{title}")
    public ResponseEntity<Flux<Book>> findByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Mono<Book>> add(@RequestBody Book book){
        return ResponseEntity.ok(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Book>> update(@PathVariable Long id, @RequestBody Book book){
        return bookService.existsById(id).block() ? ResponseEntity.ok(bookService.save(book)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteById(id));
    }

}
