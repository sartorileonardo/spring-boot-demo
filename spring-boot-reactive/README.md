# spring-boot-reactive

> Run MongoDB with WebFlux(Reactive) and MongoDB

## Run MongoDB with WebFlux(Reactive) and MongoDB


1. Pull image：`docker pull mongo:4.1`
2. Run container：`docker run -d -p 27017:27017 -v /Users/yangkai.shen/docker/mongo/data:/data/db --name mongo-4.1 mongo:4.1`
3. Stop container：`docker stop mongo-4.1`
4. Start container：`docker start mongo-4.1`


## application.yml

```yaml
spring:
  data:
    mongodb:
      host: localhost
      port: 27017
      database: book_db
      user: ${MY_MONGO_USER}
      password: ${MY_MONGO_PASSWORD}
logging:
  level:
    org.springframework.data.mongodb.core: debug
server:
  port: 8888
  error:
    include-message: always
```

## Book.java
```java
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String isbn;

    @NonNull
    private String name;

    @NonNull
    private String author;

    @NonNull
    private BigDecimal cost;

}

```


## BookRepository.java
```java
@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
```

## BookService.java
```java
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public void saveBook(Book book){
        bookRepository.save(book).subscribe();
    }

    public void saveBooks(List<Book> books){
        bookRepository.saveAll(books).subscribe();
    }

    public void deleteBook(String isbn){
        bookRepository.deleteById(isbn).subscribe();
    }

    public void deleteAllBooks(){
        bookRepository.deleteAll().subscribe();
    }

    public Mono<Book> getBook(String isbn) {
        return bookRepository.findById(isbn).switchIfEmpty(Mono.empty());
    }

    public Flux<Book> getBooks(){
        return bookRepository.findAll().switchIfEmpty(Flux.empty());
    }

}
```

## BookResource.java
```java
package com.mongo.demo.resource;

import com.mongo.demo.entity.Book;
import com.mongo.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookService.saveBook(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books){
        bookService.saveBooks(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("isbn")  String isbn){
        Mono<Book> bookSaved = bookService.getBook(isbn);
        if(Objects.nonNull(bookSaved)){
            bookService.saveBook(book);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn")  String isbn){
        bookService.deleteBook(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity deleteAllBooks(){
        bookService.deleteAllBooks();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Mono<Book>> getBook(@PathVariable("isbn") String isbn) {
        Mono<Book> bookSaved = bookService.getBook(isbn);
        return new ResponseEntity<Mono<Book>>(bookSaved, Objects.nonNull(bookSaved) ? ResponseEntity.ok().build().getStatusCode() : ResponseEntity.notFound().build().getStatusCode());
    }

    @GetMapping
    public Flux<Book> getBooks(){
        return bookService.getBooks();
    }

}
```

## BookResource.java
```java
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
```

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
