package com.mongo.demo;

import com.mongo.demo.entity.Book;
import com.mongo.demo.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private BookRepository bookRepository;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Flux<Book> books = bookRepository.findAll();
			int sizeListBooks = books.collectList().block().size();
			if (sizeListBooks == 0) {
				LOGGER.info("******* Inserting Employees to DB *******");
				bookRepository.saveAll(Arrays.asList(
						new Book("Core Java", "Kathy Sierra", new BigDecimal("1065.50").setScale(2)),
						new Book("Spring in Action", "Craig Walls", new BigDecimal("940.75").setScale(2)),
						new Book( "Hibernate in Action", "Gavin King", new BigDecimal("889.25").setScale(2)),
						new Book( "Practical MongoDB", "Shakuntala Gupta", new BigDecimal("55.50").setScale(2))
				));
			} else {
				LOGGER.info("******* Books stored in DB Size :: {}", sizeListBooks);
				LOGGER.info("******* Books stored in DB :: {}", books);
			}

		};
	}

}
