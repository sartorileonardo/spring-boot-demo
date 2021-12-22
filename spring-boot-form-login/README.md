# spring-boot-form-login

> Spring boot with Default Form Login from Spring Security

## LoginApplication.java
```java
@SpringBootApplication
public class LoginApplication {
  public static void main(String[] args) {
    SpringApplication.run(LoginApplication.class, args);
  }
}
```

## LoginController.java
```java
@RestController
public class LoginController {
  @GetMapping("/")
  public String login() {
    return "You are logged, welcome to home page!";
  }
}
```

## application.properties
```properties
spring.security.user.name=user
    spring.security.user.password=my-password-to-form-login-default
spring.main.banner-mode=off
    logging.pattern.console=%d{dd-MM-yyyy HH:mm:ss} %magenta([%thread]) %highlight(%-5level) %logger.%M - %msg%n
```

## Run Application
`mvn spring-boot:run`

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>