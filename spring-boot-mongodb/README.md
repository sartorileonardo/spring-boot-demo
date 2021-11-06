# spring-boot-demo-mongodb

> Spring boot with MongoDB

## Run MongoDB with docker


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
public interface BookRepository extends MongoRepository<Book, String> {

}
```

## BookRepositoryTest.java
```java

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void getAllBooks(){
        assertNotNull(bookRepository.findAll());
    }

    @Test
    public void getOneBook(){
        String isbn = "612afa371d094850e801fd71";
        assertNotNull(bookRepository.findById(isbn));
    }

    @Test
    public void addBook(){
        String isbn = "612afa371d094850e801fd91";
        Book book = new Book(isbn, "Inteligência artificial", "Kai-Fu Lee", new BigDecimal("29.90"));
        Book savedBook = bookRepository.save(book);
        assertEquals(isbn, savedBook.getIsbn());
    }


    @Test
    public void updateBook(){
        String isbn = "612afa371d094850e801fd91";
        Book book = bookRepository.findById(isbn).get();
        BigDecimal newCost = new BigDecimal("75.20");
        book.setCost(newCost);
        Book bookUpdated = bookRepository.save(book);
        assertEquals(newCost, bookUpdated.getCost());
    }

    @Test
    public void deleteBook(){
        String isbn = "612afa371d094850e801fd91";
        bookRepository.deleteById(isbn);
        assertTrue(bookRepository.findById(isbn).isEmpty());
    }

}

```

## BookResource.java
```java

@RestController
@RequestMapping("/v1/books")
public class BookResource {

    @Autowired
    BookRepository bookRepository;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books){
        bookRepository.saveAll(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("isbn")  String isbn){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable("isbn")  String isbn){
        bookRepository.deleteById(isbn);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity getBook(@PathVariable("isbn") String isbn) {
        return bookRepository.findById(isbn)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

}

```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
