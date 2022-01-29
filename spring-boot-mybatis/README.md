# spring-boot-mybatis

> Spring boot with MyBatis


## application.yml

```properties
server.port=8888
server.error.include-message=always
```

## Book.java
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Book {
  private Long id;
  private String isbn;
  private String name;
  private BigDecimal price;
  private String author;
}
```


## BookRepository.java
```java
@Mapper
public interface BookRepository {

  @Select("SELECT id, name, isbn, price, author FROM BOOKS")
  List<Book> findAll();

  @Select("SELECT * FROM BOOKS WHERE id = #{id}")
  Book findOne(@Param("id") Long id);

  @Delete("DELETE FROM BOOKS WHERE id = #{id}")
  void delete(Long id);

  @Options(useGeneratedKeys = true)
  @Insert("INSERT INTO BOOKS (name, isbn, price, author) VALUES( #{name}, #{isbn}, #{price}, #{author} )")
  void insert(Book book);

  @Update("UPDATE BOOKS SET name = #{name}, isbn = #{isbn}, price = #{price}, author = #{author} WHERE id = #{id}")
  void update(Book book);

}
```

## BookResource.java
```java
@RestController
@RequestMapping("/v1/books")
public class BookResource {

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
    bookService.deleteBook(id);
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

## BookRepositoryTest.java
```java
@SpringBootTest
public class BookServiceTest {

  @Autowired
  BookService bookService;

  @Test
  public void getAllBooks(){
    assertNotNull(bookService.findAll());
  }

  @Test
  public void getOneBook(){
    assertNotNull(bookService.findOne(1L));
  }

  @Test
  public void addBook(){
    Long bookId = 4L;
    Book book = Book.builder()
        .id(bookId)
        .name("The Pragmatic Programmer")
        .isbn("989328")
        .price(BigDecimal.valueOf(90.00))
        .author("Kent Beck")
        .build();
    bookService.save(book);
    assertTrue(bookService.findOne(bookId).isPresent());
  }


  @Test
  public void updateBook(){
    Long bookId = 3L;
    String newIsbn = "989328";
    Book book = Book.builder()
        .id(bookId)
        .name("The Pragmatic Programmer")
        .isbn(newIsbn)
        .price(BigDecimal.valueOf(95.00))
        .author("Kent Beck")
        .build();
    bookService.save(book);
    assertEquals(newIsbn, bookService.findOne(bookId).get().getIsbn());
  }

  @Test
  public void deleteBook(){
    Long bookId = 3L;
    bookService.delete(bookId);
    assertTrue(bookService.findOne(bookId).isEmpty());
  }


}
```

## Test Application
`mvn test`

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
