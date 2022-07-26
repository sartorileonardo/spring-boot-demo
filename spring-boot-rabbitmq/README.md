# spring-boot-rabbitmq

> Spring boot with RabbitMQ

## Run RabbitMQ with Docker
`docker run -d --hostname my-rabbit --name rabbit13 -p 8080:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management`

## Show IP Docker Container
`docker inspect <YOUR_CONTAINER_ID}>`

## Update .JAR 
`mvn clean package`

## Run Application as Producer
`java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=producer`

## Run Application as Consumer
`java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=consumer`

## Verify the queues running
http://localhost:8080/#/queues/

## application.yml

```yaml
spring:
  profiles:
    active: usage_message
  rabbitmq:
    host: 172.17.0.2 #Replace to container IP from RabbitMQ
    port: 5672
    username: guest
    password: guest

logging:
  level:
    org: ERROR

server:
  port: 8888
```