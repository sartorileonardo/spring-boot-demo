package com.example.retry.service;

import com.example.retry.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

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
