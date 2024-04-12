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
@EnableJpaRepositories(basePackages = "com.jpa.demo.repository") //
public class DemoApplication {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private PublishingCompanyRepository publishingCompanyRepository;


	@Bean
	CommandLineRunner runner() {
		return args -> {

			Author a1 = Author.builder().id(1L).firstName("Kathy").lastName("Sierra").build();
			Author a2 = Author.builder().id(2L).firstName("Craig").lastName("Walls").build();
			Author a3 = Author.builder().id(3L).firstName("Gavin").lastName("King").build();
			Author a4 = Author.builder().id(4L).firstName("Shakuntala").lastName("Gupta").build();

			authorRepository.saveAll(List.of(a1, a2, a3, a4));

			PublishingCompany p1 = PublishingCompany.builder().id(1L).name("P1").build();
			PublishingCompany p2 = PublishingCompany.builder().id(2L).name("P2").build();
			PublishingCompany p3 = PublishingCompany.builder().id(3L).name("P3").build();
			PublishingCompany p4 = PublishingCompany.builder().id(4L).name("P4").build();

			publishingCompanyRepository.saveAll(List.of(p1, p2, p3, p4));

			List<Book> books = bookRepository.findAll();
			if (books.size() == 0) {
				LOGGER.info("******* Inserting Employees to DB *******");
				bookRepository.saveAll(Arrays.asList(
						new Book(1L, UUID.randomUUID().toString(), "Core Java", new BigDecimal("1065.50").setScale(2), List.of(a1), p1),
						new Book(2L, UUID.randomUUID().toString(), "Spring in Action", new BigDecimal("940.75").setScale(2), List.of(a2), p2),
						new Book(3L, UUID.randomUUID().toString(), "Hibernate in Action", new BigDecimal("889.25").setScale(2), List.of(a3), p3),
						new Book(4L, UUID.randomUUID().toString(), "Practical MongoDB", new BigDecimal("55.50").setScale(2), List.of(a4), p4)
				));
			} else {
				LOGGER.info("******* Books stored in DB Size :: {}", books.size());
				LOGGER.info("******* Books stored in DB :: {}", books);
			}

		};
	}

}
