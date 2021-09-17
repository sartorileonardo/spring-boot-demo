package com.example.kafka_prod_cons;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private KafkaTemplate<String, String> kafkaTemplate;
    private Gson jsonConverter;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter){
        this.kafkaTemplate = kafkaTemplate;
        this.jsonConverter = jsonConverter;
    }

    @PostMapping
    public void send(@RequestBody Message message){
        kafkaTemplate.send("myTopic", jsonConverter.toJson(message));
    }

    @KafkaListener(topics = "myTopic")
    public void getFromKafka(String message){
        Message msg = jsonConverter.fromJson(message, Message.class);
        System.out.println("Apache Kafka received: \n".concat(msg.toString()));
    }
}
