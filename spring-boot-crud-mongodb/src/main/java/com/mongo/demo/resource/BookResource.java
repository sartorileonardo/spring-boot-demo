package com.mongo.demo.resource;

import com.mongo.demo.entity.Book;
import com.mongo.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    BookRepository bookRepository;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books){
        bookRepository.saveAll(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("isbn")  String isbn){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn")  String isbn){
        bookRepository.deleteById(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity getBook(@PathVariable("isbn") String isbn) {
        return bookRepository.findById(isbn)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

}
