package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }
}
