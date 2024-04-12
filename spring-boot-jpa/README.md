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
package com.jpa.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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
    private Long id;

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
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
```

## BookRepositoryTest.java
```java

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    private List<Author> authors;

    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        //Given
        bookRepository.deleteAll();
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(8L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @AfterEach
    public void cleanup() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return a list from saved books")
    void getAllBooks() {
        //Given
        bookRepository.save(book);

        //When
        List<Book> books = bookRepository.findAll();

        //Then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Should return a existing book")
    public void getOneBook() {
        //Given & When
        Book savedBook = bookRepository.save(book);

        //Then
        assertNotNull(savedBook);
        assertEquals(book.getId(), savedBook.getId());
    }

    @Test
    @DisplayName("Should save a new book with sucess")
    void addBook() {
        //Then
        Book savedBook = bookRepository.save(book);

        //When
        assertNotNull(savedBook);
    }


    @Test
    @DisplayName("Should update existing book with sucess")
    void updateBook() {
        //Given
        Book savedBook = bookRepository.save(book);

        //When
        String newBookName = "NewBookName";
        savedBook.setName(newBookName);
        Book bookUpdated = bookRepository.save(savedBook);

        //Then
        assertNotNull(bookUpdated);
        assertEquals(newBookName, bookUpdated.getName());
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() {
        //Given
        Book savedBook = bookRepository.save(book);

        //When
        bookRepository.deleteById(savedBook.getId());

        //Then
        assertTrue(bookRepository.findById(savedBook.getId()).isEmpty());
    }

}
```

## BookService.java
```
@Service
public class BookService {

    public static final String MSG_NO_RECORDS_FOUND_FOR_THIS_ID = "No records found for this ID!";
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!")));
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        Book entity = bookRepository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MSG_NO_RECORDS_FOUND_FOR_THIS_ID));

        entity.setName(book.getName());
        entity.setIsbn(book.getIsbn());
        entity.setCost(book.getCost());
        entity.setAuthor(book.getAuthor());
        entity.setPublishingCompany(book.getPublishingCompany());

        return bookRepository.save(entity);
    }

    public List<Book> saveAll(List<Book> books) {
        return bookRepository.saveAll(books);
    }

}
```

## BookServiceTest.java
```
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    private List<Author> authors;

    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        //Given
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(1L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @Test
    @DisplayName("Should return a list from saved books")
    void getAllBooks() {
        //Given
        given(bookRepository.findAll()).willReturn(List.of(book));

        //When
        List<Book> books = bookService.findAll();

        //Then
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("Should return a existing book")
    public void getOneBook() {
        //Given
        given(bookRepository.findById(anyLong())).willReturn(Optional.of(book));

        //When
        Optional<Book> savedBook = bookService.findById(book.getId());

        //Then
        assertTrue(savedBook.isPresent());
        assertEquals(book.getId(), savedBook.get().getId());
    }

    @Test
    @DisplayName("Should save a new book with success")
    void addBook() {
        // Given
        given(bookRepository.save(any(Book.class))).willReturn(book);

        // When
        Book savedBook = bookService.save(book);

        // Then
        assertNotNull(savedBook);
        assertEquals(book.getName(), savedBook.getName());

        // Optionally, verify that the mock was called
        verify(bookRepository).save(any(Book.class));
    }



    @Test
    @DisplayName("Should update existing book with sucess")
    void updateBook() {
        // Given
        book.setName("updatedBookName");
        given(bookRepository.save(any(Book.class))).willReturn(book);

        // When
        Book savedBook = bookService.save(book);

        // Then
        assertNotNull(savedBook);
        assertEquals(book.getName(), savedBook.getName());

        // Optionally, verify that the mock was called
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    @DisplayName("Should delete an existing book")
    void deleteBook() {
        //Given
        willDoNothing().given(bookRepository).deleteById(book.getId());

        //When
        bookService.deleteById(book.getId());

        //Then
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

}
```

## BookController.java
```java

@RestController
@RequestMapping("/v1/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity insertBook(@RequestBody Book book) {
        Book savedBook = bookService.save(book);
        return ResponseEntity.of(Optional.of(savedBook));
    }

    @PostMapping("/addall")
    public ResponseEntity insertBooks(@RequestBody List<Book> books) {
        bookService.saveAll(books);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.update(book));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteall/")
    public ResponseEntity<?> deleteAllBooks() {
        bookService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.of(bookService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookService.findAll();
    }

}

```

## BookControllerTest.java
```java

@WebMvcTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService service;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private PublishingCompanyRepository publishingCompanyRepository;


    private Book book;

    private List<Author> authors;

    private PublishingCompany publishingCompany;

    @BeforeEach
    public void setup() {
        //Given
        publishingCompany = PublishingCompany.builder().id(1L).name("ABC").build();
        authors = List.of(Author.builder().id(1L).firstName("Joao").lastName("Silva").build());
        book = Book.builder()
                .id(8L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("Inteligência artificial")
                .cost(new BigDecimal("29.90"))
                .build();
    }

    @Test
    @DisplayName("Should return a list from saved books")
    void getAllBooks() throws Exception {
        //Given
        Book otherBook = Book.builder()
                .id(9L)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("AnyBook")
                .cost(new BigDecimal("10.50"))
                .build();

        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(otherBook);

        given(service.findAll()).willReturn(books);

        //When
        ResultActions response = mockMvc.perform(get("/v1/books"));

        //Then
        response.
                andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(books.size())));
    }

    @Test
    @DisplayName("Should return a existing book")
    public void getOneBook() throws Exception {
        //Given
        long bookId = 10L;
        given(service.findById(bookId)).willReturn(Optional.ofNullable(book));

        //When
        ResultActions response = mockMvc.perform(get("/v1/books/{id}", bookId));

        //Then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.name", is(book.getName())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())));
    }

    @Test
    @DisplayName("Should return a exception when book not found")
    public void getOneBookWhenBookNotFound() throws Exception {
        //Given
        long bookId = 10L;
        given(service.findById(bookId)).willThrow(ResourceNotFoundException.class);

        //When
        ResultActions response = mockMvc.perform(get("/v1/books/{id}", bookId));

        //Then
        response
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should save a new book with sucess")
    void addBook() throws Exception {
        //Given
        given(service.save(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //When
        ResultActions response = mockMvc.perform(post("/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)));

        //Then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(book.getName())));
    }


    @Test
    @DisplayName("Should update existing book with sucess")
    void updateBook() throws Exception {
        //Given
        long bookId = 10L;
        Book updatedBook = book = Book.builder()
                .id(bookId)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("updatedBook")
                .cost(new BigDecimal("15.50"))
                .build();
        given(service.findById(bookId)).willReturn(Optional.ofNullable(book));
        given(service.update(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //When
        ResultActions response = mockMvc.perform(put("/v1/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));

        //Then
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updatedBook.getName())));
    }

    @Test
    @DisplayName("Should exception when try update and not existing book")
    void updateBookWhenNotExistingBook() throws Exception {
        //Given
        long bookId = 10L;
        Book updatedBook = book = Book.builder()
                .id(bookId)
                .author(authors)
                .publishingCompany(publishingCompany)
                .isbn(UUID.randomUUID().toString())
                .name("updatedBook")
                .cost(new BigDecimal("15.50"))
                .build();
        given(service.findById(bookId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(1));

        //When
        ResultActions response = mockMvc.perform(put("/v1/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedBook)));

        //Then
        response
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a existing book")
    void deleteBook() throws Exception {
        //Given
        long bookId = 10L;
        willDoNothing().given(service).deleteById(bookId);

        //When
        ResultActions response = mockMvc.perform(delete("/v1/books/{id}", bookId));

        //Then
        response
                .andDo(print())
                .andExpect(status().isNoContent());

    }

}

```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
