# spring-boot-swagger-documentation

> Spring boot with Swagger Documentation

## Book.java
```java
@ApiModel(value = "Book", description = "Model to represent a Book into system")
public class Book {
    @ApiModelProperty(value = "Book's ISBN", required = true)
    private String isbn;

    @ApiModelProperty(value = "Book's name", required = true)
    private String name;

    @ApiModelProperty(value = "Book's author", required = true)
    private String author;

    @ApiModelProperty(value = "Book's publication date")
    private LocalDateTime publicationDate;

    @ApiModelProperty(value = "Book's price")
    private BigDecimal price;
    
    //Getters, Setters and Constructors
}

```


## BookService.java
```java
@Service
public class BookService {
    public List<Book> getBooks(){
        return List.of(
                new Book(UUID.randomUUID().toString(), "Clean Arquitecture", "Robert Martin", LocalDateTime.now(), new BigDecimal("150.50").setScale(2, RoundingMode.HALF_DOWN)),
                new Book(UUID.randomUUID().toString(), "Mastering Microservices with Java", "Sourabh Sharma", LocalDateTime.now(), new BigDecimal("375.25").setScale(2, RoundingMode.HALF_DOWN)),
                new Book(UUID.randomUUID().toString(), "Spring in Action", "Craig Walls", LocalDateTime.now(), new BigDecimal("311.75").setScale(2, RoundingMode.HALF_DOWN))
        );
    }
}
```

## BookController.java
```java
@Api(value = "Books API")
@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Getting all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting all books"),
            @ApiResponse(code = 400, message = "Business error"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }
}
```

## SwaggerConfig.java
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
```

## BookServiceTest.java
```java
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void getBooks() {
        assertNotNull(bookService.getBooks());
        assertFalse(bookService.getBooks().isEmpty());
    }
}
```

## Run Application
`gradle bootRun`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
