# spring-boot-actuator

> Spring boot with Actuator.

## DemoApplication.java
```java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```
## DemoApplicationTest.java
```java
@SpringBootTest
public class DemoApplicationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToInfoEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/info";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToHealthEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/health";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToMetricsEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/metrics";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToSpecificMetricEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/metrics/jvm.memory.used";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToLoggersEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/loggers";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToEnvironmentEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/env";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToBeansEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/beans";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToThreadDumpEndpoint() throws Exception{
        String url = "http://localhost:8888/actuator/threaddump";
        ResponseEntity<Map> entity = this.restTemplate.getForEntity(url, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
```

## application.properties
```properties
# server tomcat
server.port=8888

# actuator
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=*
management.endpoint.beans.cache.time-to-live=30s
info.app.name=@project.name@
info.app.description=@project.description@
info.app.version=@project.version@ 
info.app.encoding=@project.build.sourceEncoding@
info.app.java.version=@java.version@
```