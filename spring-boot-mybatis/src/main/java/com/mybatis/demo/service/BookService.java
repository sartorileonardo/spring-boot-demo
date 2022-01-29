package com.mybatis.demo.service;

import com.mybatis.demo.entity.Book;
import com.mybatis.demo.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  BookRepository bookRepository;

  public List<Book> findAll(){
    return bookRepository.findAll();
  }

  public Optional<Book> findOne(Long id){
    return Optional.ofNullable(bookRepository.findOne(id));
  }

  public void save(Book book) {
    if (findOne(book.getId()).isEmpty()) {
      bookRepository.insert(book);
    } else {
      bookRepository.update(book);
    }
  }

  public void delete(Long id) {
    bookRepository.delete(id);
  }
}
