package com.r2dbc.demo.repository;

import com.r2dbc.demo.entity.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends R2dbcRepository<Book, Long> {
    @Query("SELECT id, title, author, price FROM book WHERE UPPER(title) LIKE CONCAT('%',UPPER(:title),'%')")
    Flux<Book> findByTitle(String title);
}
