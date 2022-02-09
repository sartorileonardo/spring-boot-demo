# spring-boot-openfeign

> Spring boot with OpenFeign


## application.yml

```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 15000
        readTimeout: 15000
        loggerLevel: basic
server:
  port: 8888
  error:
    include-message: always
```

## DictionaryClient.java
```java
@FeignClient(value = "opendictionary", url = "https://api.dictionaryapi.dev/api/v2/")
public interface DictionaryClient {
  @RequestMapping(method = RequestMethod.GET, value = "entries/en/{word}", produces = "application/json")
  List<Map<String, Object>> getAnswer(@PathVariable("word") String word);
}
```


## DictionaryService.java
```java
@Service
public class DictionaryService {

  private static String SYNONYM = "synonyms";
  private static String ANTONYM = "antonyms";

  @Autowired
  DictionaryClient openDictionaryClient;

  public List<Map<String, Object>> getAnswer(String word){
    return openDictionaryClient.getAnswer(word);
  }

  public List<String> getSynonymousWord(String word) {
    return DictionaryAdapter.getListAnswersToListWordsByClassificationWord(getAnswer(word), SYNONYM);
  }

  public List<String> getAntonymsWord(String word) {
    return DictionaryAdapter.getListAnswersToListWordsByClassificationWord(getAnswer(word), ANTONYM);
  }
}
```

## DictionaryResource.java
```java
@RestController
@RequestMapping("/v1/dictionary")
public class DictionaryResource {

  @Autowired
  DictionaryService bookService;

  @GetMapping("/{word}")
  public ResponseEntity getAnswer(@PathVariable("word") String word) {
    return ResponseEntity.ok(bookService.getAnswer(word));
  }

  @GetMapping("/synonyms/{word}")
  public ResponseEntity getSynonymousWord(@PathVariable("word") String word) {
    return ResponseEntity.ok(bookService.getSynonymousWord(word));
  }

  @GetMapping("/antonyms/{word}")
  public ResponseEntity getAntonymsWord(@PathVariable("word") String word) {
    return ResponseEntity.ok(bookService.getAntonymsWord(word));
  }

}
```

## DictionaryServiceTest.java
```java
@SpringBootTest
public class DictionaryServiceTest {

  private static String MY_WORD = "sweet";
  private static String SYNONYM_MY_WORD = "sugary";
  private static String ANTONYM_MY_WORD = "sour";
  private static String NO_RESULT_MY_WORD = "kkkkkkk";

  @Autowired
  DictionaryService bookService;

  @Test
  public void shouldGetAnswerWithSucess(){
    assertFalse(bookService.getAnswer(MY_WORD).isEmpty());
  }

  @Test
  public void shouldGetSynonymsWithSucess(){
    assertTrue(bookService.getSynonymousWord(MY_WORD).contains(SYNONYM_MY_WORD));
  }

  @Test
  public void shouldGetAntonymsWithSucess(){
    assertTrue(bookService.getAntonymsWord(MY_WORD).contains(ANTONYM_MY_WORD));
  }

  @Test
  public void shouldReturnNoResult(){
    assertThrows(FeignException.class, () -> bookService.getAnswer(NO_RESULT_MY_WORD).stream().findFirst());
  }

}
```

## Test Application
`mvn test`

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
