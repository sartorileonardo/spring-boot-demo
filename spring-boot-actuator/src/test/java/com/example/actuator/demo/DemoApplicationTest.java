package com.example.actuator.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
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