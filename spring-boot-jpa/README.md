# spring-boot-jpa

> Spring boot with Java Persistence API (JPA)


## application.yml

```properties
server.port=8888
spring.datasource.initialize=true
spring.datasource.platform=h2
spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
server.error.include-message=always
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author", insertable = true, updatable = true)
    private List<Author> author;

    @OneToOne
    @JoinColumn(name = "id_publising_company", insertable = true, updatable = true)
    private PublishingCompany publishingCompany;

}

```


## BookRepository.java
```java
public interface BookRepository extends JpaRepository<Book, Integer> {
}
```

## BookRepositoryTest.java
```java

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublishingCompanyRepository publishingCompanyRepository;

    @Test
    public void getAllBooks(){
        assertNotNull(bookRepository.findAll());
    }

    @Test
    public void getOneBook(){
        Integer bookId = 1;
        assertNotNull(bookRepository.findById(bookId));
    }

    @Test
    public void addBook(){
        PublishingCompany publishingCompany = publishingCompanyRepository.getById(1);
        List<Author> authors = authorRepository.findAllById(List.of(1, 2));
        Integer bookId = 5;
        Book book = new Book(bookId, UUID.randomUUID().toString(), "Inteligência artificial", new BigDecimal("29.90"), authors, publishingCompany);
        Book savedBook = bookRepository.save(book);
        assertEquals(bookId, savedBook.getId());
    }


    @Test
    public void updateBook(){
        Integer bookId = 4;
        Book book = bookRepository.findById(bookId).get();
        BigDecimal newCost = new BigDecimal("75.20");
        book.setCost(newCost);
        Book bookUpdated = bookRepository.save(book);
        assertEquals(newCost, bookUpdated.getCost());
    }

    @Test
    public void deleteBook(){
        Integer bookId = 4;
        bookRepository.deleteById(bookId);
        assertTrue(bookRepository.findById(bookId).isEmpty());
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

    @PutMapping("/{id}")
    public ResponseEntity updateBook(@RequestBody Book book, @PathVariable("id")  Integer id){
        bookRepository.save(book);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id")  Integer id){
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks(){
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable("id") Integer id) {
        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

}

```