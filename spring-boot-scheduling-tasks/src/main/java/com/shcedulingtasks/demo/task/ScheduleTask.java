package com.shcedulingtasks.demo.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.ConnectException;
import java.time.LocalDateTime;
import java.util.Map;

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
