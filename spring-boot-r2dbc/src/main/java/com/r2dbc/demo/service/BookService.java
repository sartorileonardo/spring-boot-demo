package com.r2dbc.demo.service;

import com.r2dbc.demo.entity.Book;
import com.r2dbc.demo.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Flux<Book> findAll() {
        return bookRepository.findAll().switchIfEmpty(Flux.empty());
    }

    public Mono<Book> findById(Long id) {
        return bookRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    public Flux<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title).switchIfEmpty(Flux.empty());
    }

    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Boolean> existsById(Long id) {
        return bookRepository.existsById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
}
