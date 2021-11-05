package com.mongo.demo.resource;

import com.mongo.demo.entity.Book;
import com.mongo.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books){
        bookService.saveBooks(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("isbn")  String isbn){
        Mono<Book> bookSaved = bookService.getBook(isbn);
        if(Objects.nonNull(bookSaved)){
            bookService.saveBook(book);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn")  String isbn){
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity deleteAllBooks(){
        bookService.deleteAllBooks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Mono<Book>> getBook(@PathVariable("isbn") String isbn) {
        Mono<Book> bookSaved = bookService.getBook(isbn);
        return new ResponseEntity<Mono<Book>>(bookSaved, Objects.nonNull(bookSaved) ? ResponseEntity.ok().build().getStatusCode() : ResponseEntity.notFound().build().getStatusCode());
    }

    @GetMapping
    public Flux<Book> getBooks(){
        return bookService.getBooks();
    }

}
