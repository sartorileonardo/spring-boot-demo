# spring-boot-demo-mongodb

> Spring boot with Schedule Task that verify the Dollar price.

## ScheduleTask.java
```java
@Component
public class ScheduleTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduleTask.class);

    @Scheduled(fixedRate = 25000)
    public void showMessageLog() throws URISyntaxException, IOException, InterruptedException {
        log.info("The dollar price is: " + getDollarPrice());
        log.info("Date/hour: " + LocalDateTime.now() + "\n");
    }

    private String getDollarPrice() throws URISyntaxException, IOException, InterruptedException {

        HttpResponse<String> response = getConnectionWithExternalHttpEndpoint();

        String dollarPrice = extractFromResponse(response.body());

        return dollarPrice;
    }

    private String extractFromResponse(String responseBody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = (Map<String, Object>) mapper.readValue(responseBody, Map.class).get("USDBRL");
        String askFromDollar = responseMap.get("ask").toString();
        return askFromDollar;
    }

    private HttpResponse getConnectionWithExternalHttpEndpoint() throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)  // this is the default
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://economia.awesomeapi.com.br/json/last/USD-BRL"))
                .version(HttpClient.Version.HTTP_2)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(response.statusCode() != 200){
            throw new ConnectException("Connect fail to (economia.awesomeapi.com.br). \nStatus code: " + response.statusCode());
        }

        return response;
    }

}

```
## DemoApplication.java
```java
@SpringBootApplication
@EnableScheduling
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
```

## Run Application
`mvn spring-boot:run`
