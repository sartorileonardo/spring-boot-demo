# spring-boot-flyway

> Spring boot with Flyway


## application.properties

```yaml
spring:
  datasource:
    driverClassName: org.h2.Driver
    url: jdbc:h2:mem:forum
    username: sa
    password:
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    properties:
      hibernate:
        show_sql: true
        format_sql: true
  h2:
    console:
      enabled: true

server:
  port: 8888
```

## Book.java
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "book")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String name;
    private BigDecimal price;
    private String author;
}
```

## BookRepository.java
```java
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
```

## BookService.java
```java
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Optional<Book> findOne(Long id){
        return bookRepository.findById(id);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}

```

## BookController.java
```java
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookService.save(book);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("id")  Long id){
        bookService.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id")  Long id){
        bookService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Long id) {
        return bookService.findOne(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity getBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

}
```

## V1__create_table_book.sql
```roomsql
CREATE TABLE IF NOT EXISTS `BOOKS`(
  `id`      BIGINT AUTO_INCREMENT PRIMARY KEY,
  `name`    VARCHAR(100) NOT NULL,
  `isbn`    VARCHAR(50) NOT NULL,
  `price`   NUMERIC(5, 2) NOT NULL,
  `author`  VARCHAR(100) NOT NULL
);
```

## V2__insert_book.sql
```roomsql
INSERT INTO BOOKS VALUES(default, 'Java Efective', '12345', 50.00, 'Joshua Bloch');
INSERT INTO BOOKS VALUES(default, 'Craig Walls', '67891', 75.00, 'Joshua Bloch');
INSERT INTO BOOKS VALUES(default, 'The Intelligent Investor', '92839', 45.00, 'Benjamin Graham');
```

## Test Application
`mvn test`

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
