# spring-boot-actuator

> Spring boot with Google Translate API.

## Translate.java
```java
@SpringBootApplication
public class Translate {

    public static void main(String[] args) {

        SpringApplication.run(Translate.class, args);
    }
}
```
## TranslateControllerTest.java
```java
@SpringBootTest
@AutoConfigureMockMvc
class TranslateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTranslate() throws Exception {
        mockMvc
                .perform(post("/translate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(
                                asJsonString(
                                        new TranslateDTOIn("Hi", "en", "pt"))))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .equalsIgnoreCase("Oi");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

## application.properties
```properties
# server tomcat
server.port=8888

# You Google API KEY
translate.key=AIzaSyACJybEm6lyelnYHZzXfydtk-V6-Uz48bQ

```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
