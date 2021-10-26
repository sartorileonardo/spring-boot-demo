package com.itext.demo.service;

import com.itext.demo.entity.Book;
import com.itext.demo.repository.BookRepository;
import com.itext.demo.utils.GeneratePdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> saveBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBooks() {
        bookRepository.deleteAll();
    }

    @Override
    public Optional<Book> getBook(Integer id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public InputStreamResource getContentBooksReport() {
        ByteArrayInputStream byteArray = GeneratePdfReport.booksReport(bookRepository.findAll());
        return new InputStreamResource(byteArray);
    }
}
