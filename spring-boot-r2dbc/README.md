# spring-boot-reactive-r2dbc

> Run MongoDB with R2DBC and H2

## application.yml

```yml
server:
  port: 8888
  error:
    include-message: always
```

## Book.java
```java
@Value
@Builder
@ToString
public class Book {
    @Id
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
}
```


## BookRepository.java
```java
@Repository
public interface BookRepository extends R2dbcRepository<Book, Long> {
    @Query("SELECT id, title, author, price FROM book WHERE UPPER(title) LIKE CONCAT('%',UPPER(:title),'%')")
    Flux<Book> findByTitle(String title);
}
```

## BookService.java
```java
@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Flux<Book> findAll() {
        return bookRepository.findAll();
    }

    public Mono<Book> findById(Long id) {
        return bookRepository.findById(id).doOnNext(book -> log.info(book.toString()));
    }

    public Flux<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title).doOnNext(book -> log.info(book.toString()));
    }

    public Mono<Book> save(Book book) {
        return bookRepository.save(book);
    }

    public Mono<Boolean> existsById(Long id) {
        return bookRepository.existsById(id);
    }

    public Mono<Void> deleteById(Long id) {
        return bookRepository.deleteById(id);
    }
}
```

## BookController.java
```java
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Flux<Book> findAll(){
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Book>> findById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findById(id));
    }

    @GetMapping("/findbytitle/{title}")
    public ResponseEntity<Flux<Book>> findByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Mono<Book>> add(@RequestBody Book book){
        return ResponseEntity.ok(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<Book>> update(@PathVariable Long id, @RequestBody Book book){
        return bookService.existsById(id).block() ? ResponseEntity.ok(bookService.save(book)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteById(id));
    }

}

```

## BookControllerTest.java
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    private static final Long ID_BOOK = 1L;
    private static final String TITLE_BOOK = "abc";

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @Test
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void add() throws Exception {
        String bodyBook = "{\n" +
                "    \"title\": \"ABC\",\n" +
                "    \"author\": \"CDE\",\n" +
                "    \"price\": 500.00\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/book")
                        .content(bodyBook)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(3)
    @Test
    void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/" + ID_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(4)
    @Test
    void findByTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/book/findbytitle/" + TITLE_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(5)
    @Test
    void update() throws Exception {
        String bodyBook = "{\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"XPTO\",\n" +
                "    \"author\": \"testAuthor\",\n" +
                "    \"price\": 40.00\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/book/" + ID_BOOK)
                        .content(bodyBook)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Order(6)
    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/book/" + ID_BOOK)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
```

## Test Application
`mvn test`

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
