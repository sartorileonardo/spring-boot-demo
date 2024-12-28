package com.jpa.demo;

import com.jpa.demo.entity.Author;
import com.jpa.demo.entity.Book;
import com.jpa.demo.entity.PublishingCompany;
import com.jpa.demo.repository.AuthorRepository;
import com.jpa.demo.repository.BookRepository;
import com.jpa.demo.repository.PublishingCompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jpa.demo.repository")
public class DemoApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {

            // Autores
            Author a1 = Author.builder().id(1L).firstName("Kathy").lastName("Sierra").build();
            Author a2 = Author.builder().id(2L).firstName("Craig").lastName("Walls").build();
            Author a3 = Author.builder().id(3L).firstName("Gavin").lastName("King").build();
            Author a4 = Author.builder().id(4L).firstName("Shakuntala").lastName("Gupta").build();

            authorRepository.saveAll(List.of(a1, a2, a3, a4));

            // Editoras
            PublishingCompany p1 = PublishingCompany.builder().id(1L).name("Addison-Wesley").build();
            PublishingCompany p2 = PublishingCompany.builder().id(2L).name("O'Reilly Media").build();
            PublishingCompany p3 = PublishingCompany.builder().id(3L).name("Manning").build();
            PublishingCompany p4 = PublishingCompany.builder().id(4L).name("Apress").build();

            publishingCompanyRepository.saveAll(List.of(p1, p2, p3, p4));

            // Buscar livros existentes
            List<Book> books = bookRepository.findAll();
            if (books.size() == 0) {
                LOGGER.info("******* Inserting Books to DB *******");

                // Salvar livros com as respectivas editoras
                bookRepository.saveAll(Arrays.asList(
                        Book.builder()
                                .id(1L)
                                .isbn(UUID.randomUUID().toString())  // Gerar UUID ou passar diretamente
                                .name("Core Java")  // Nome do livro
                                .cost(new BigDecimal("1065.50").setScale(2))
                                .authors(List.of(a1))
                                .publishingCompany(p1)  // Editora Addison-Wesley
                                .build(),
                        Book.builder()
                                .id(2L)
                                .isbn(UUID.randomUUID().toString())
                                .name("Spring in Action")
                                .cost(new BigDecimal("940.75").setScale(2))
                                .authors(List.of(a2))
                                .publishingCompany(p2)  // Editora O'Reilly Media
                                .build(),
                        Book.builder()
                                .id(3L)
                                .isbn(UUID.randomUUID().toString())
                                .name("Hibernate in Action")
                                .cost(new BigDecimal("889.25").setScale(2))
                                .authors(List.of(a3))
                                .publishingCompany(p3)  // Editora Manning
                                .build(),
                        Book.builder()
                                .id(4L)
                                .isbn(UUID.randomUUID().toString())
                                .name("Practical MongoDB")
                                .cost(new BigDecimal("55.50").setScale(2))
                                .authors(List.of(a4))
                                .publishingCompany(p4)  // Editora Apress
                                .build()
                ));

                LOGGER.info("******* Standard books added successfully *******");

            } else {
                LOGGER.info("******* Books stored in DB Size :: {}", books.size());
                LOGGER.info("******* Books stored in DB :: {}", books);
            }
        };
    }
}
