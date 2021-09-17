# spring-boot-apache-kafka-producer-and-consumer

> Spring boot with Apache Kafka (Producer and Consumer).

## Run Apache Kafka (Into Kafka Home Directory)

1. Run Zookepeer server：`bin/zookeeper-server-start.sh config/zookeeper.properties`
2. Run Kafka server：`bin/kafka-server-start.sh config/server.properties`
3. Listener a topic：`bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myTopic`

## KafkaProdConsApplication.java
```java
@SpringBootApplication
public class KafkaProdConsApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProdConsApplication.class, args);
    }
}
```
## Message.java
```java
public class Message {
    private String id;
    private String text;
    private LocalDate dateSend;

    public Message() {
        this.id = UUID.randomUUID().toString();
        this.dateSend = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDate dateSend) {
        this.dateSend = dateSend;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", dateSend=" + dateSend +
                '}';
    }
}
```

## KafkaConfig.java
```java

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, Message> producerFactory(){
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(){
        return new KafkaTemplate(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "myGroupId");

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public Gson jsonConverter(){
        return new Gson();
    }
}
```

## KafkaController.java
```java
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

```
