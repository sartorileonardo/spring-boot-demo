# spring-boot-retry

> Spring Boot with Spring Retry


## MessageController.java

```java
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{keyWord}")
    public ResponseEntity getMessage(@PathVariable("keyWord") String keyWord){
        log.info("Loading message...");
        return messageService.getMessage(keyWord).isPresent() ? ResponseEntity.ok(messageService.getMessage(keyWord).get()) : ResponseEntity.notFound().build();
    }
}
```

## Message.java
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {
    private String id;
    private String text;
    private String author;
    private LocalDateTime sendDate;
}
```
## MessageService.java
```java
@Service
@Slf4j
public class MessageService {

    @Autowired
    private RestTemplate restTemplate;

    @Retryable(maxAttempts = 3, value = Exception.class, backoff = @Backoff(delay = 2000))
    public Optional<Message> getMessage(String keyWord) {
        Map responseMap = restTemplate.getForEntity(URI.create("https://pensador-api.vercel.app/?term=" + keyWord + "&max=5"), Map.class).getBody();
        List<Map<String, Object>> phases = !responseMap.isEmpty() ? (List<Map<String, Object>>) responseMap.get("frases") : Collections.emptyList();
        Optional<Message> msg = null;
        if(!phases.isEmpty()){
            msg = phases.stream().findAny().map(phrase -> Message.builder().id(UUID.randomUUID().toString()).author(phrase.get("autor").toString()).text(phrase.get("texto").toString()).sendDate(LocalDateTime.now()).build());
            log.info("Message was loaded with sucess!");
        }
        return msg;
    }
}
```

## DemoApplication.java
```java
@EnableRetry
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
```

## application.yml

```properties
server.error.include-message=always
```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>


