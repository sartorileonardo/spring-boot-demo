package com.jpa.demo.resource;

import com.jpa.demo.entity.Book;
import com.jpa.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("id")  Integer id){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id")  Integer id){
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Integer id) {
        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

}
