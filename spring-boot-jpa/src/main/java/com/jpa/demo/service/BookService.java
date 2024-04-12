package com.jpa.demo.service;

import com.jpa.demo.entity.Book;
import com.jpa.demo.exception.ResourceNotFoundException;
import com.jpa.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    public static final String MSG_NO_RECORDS_FOUND_FOR_THIS_ID = "No records found for this ID!";
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")));
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

    public Book update(Book book) {
        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MSG_NO_RECORDS_FOUND_FOR_THIS_ID));

        entity.setName(book.getName());
        entity.setIsbn(book.getIsbn());
        entity.setCost(book.getCost());
        entity.setAuthor(book.getAuthor());
        entity.setPublishingCompany(book.getPublishingCompany());

        return bookRepository.save(entity);
    }

    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }


}
