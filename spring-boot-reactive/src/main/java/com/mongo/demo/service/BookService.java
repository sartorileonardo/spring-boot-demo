package com.mongo.demo.service;

import com.mongo.demo.entity.Book;
import com.mongo.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void saveBook(Book book){
        bookRepository.save(book).subscribe();
    }

    public void saveBooks(List<Book> books){
        bookRepository.saveAll(books).subscribe();
    }

    public void deleteBook(String isbn){
        bookRepository.deleteById(isbn).subscribe();
    }

    public void deleteAllBooks(){
        bookRepository.deleteAll().subscribe();
    }

    public Mono<Book> getBook(String isbn) {
        return bookRepository.findById(isbn).switchIfEmpty(Mono.empty());
    }

    public Flux<Book> getBooks(){
        return bookRepository.findAll().switchIfEmpty(Flux.empty());
    }

}
