package com.itext.demo.service;

import com.itext.demo.entity.Book;
import org.springframework.core.io.InputStreamResource;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    Book saveBook(Book book);
    List<Book> saveBooks(List<Book> books);
    void deleteBook(Integer id);
    void deleteBooks();
    Optional<Book> getBook(Integer id);
    List<Book> getBooks();
    InputStreamResource getContentBooksReport();
}
