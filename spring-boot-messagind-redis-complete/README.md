# spring-boot-messaging-redis

> Spring boot with No SQL Redis database sending message.

## Run Redis Database with Docker
```bash
docker run -d -p 6379:6379 -i -t redis:3.2.5-alpine
```


## SpringBootmessagingRedisApplication.java
```java
@SpringBootApplication
public class SpringBootmessagingRedisApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootmessagingRedisApplication.class);

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(){
        return new Receiver();
    }

    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory){
        return new StringRedisTemplate(connectionFactory);
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext ctx = SpringApplication.run(SpringBootmessagingRedisApplication.class, args);

        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);

        Receiver receiver = ctx.getBean(Receiver.class);

        while (receiver.getCount() == 0){
            LOGGER.info("Sending message...");
            template.convertAndSend("chat", "Hello from NoSQL Redis DB!");
            Thread.sleep(500L);
        }

        System.exit(0);
    }

}
```
## Receiver.java
```java

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);
    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message){
        LOGGER.info("Received message: " + message + " at " + LocalDateTime.now());
        counter.incrementAndGet();
    }

    public int getCount(){
        return counter.get();
    }
    
}
```
