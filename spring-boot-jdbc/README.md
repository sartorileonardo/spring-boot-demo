# spring-boot-messaging-redis

> Spring boot with JDBC persistence.



## JdbcApplication.java
```java
@SpringBootApplication
public class JdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }
}
```
## Message.java
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Message {
    private Integer id;
    private String text;
}
```

## IMessageService.java
```java
public interface IMessageService {
    List<Message> getMessages();
    Message getMessageById(Integer id);
    List<Message> getMessageByText(String text);
    boolean insert(Message message);
    boolean update(Message message, Integer id);
    boolean delete(Integer id);
}
```

## MessageService.java
```java
@Service
public class MessageService implements IMessageService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Message> getMessages() {
        var sqlQuery = "SELECT * FROM message";
        return jdbcTemplate.query(sqlQuery, BeanPropertyRowMapper.newInstance(Message.class));
    }

    public Message getMessageById(Integer id) {
        var sqlQuery = "SELECT * FROM message WHERE id = ?";
        Object[] param = new Object[]{id};
        return jdbcTemplate.queryForObject(sqlQuery, param, BeanPropertyRowMapper.newInstance(Message.class));
    }

    public List<Message> getMessageByText(String text) {
        var sqlQuery = "SELECT * FROM message WHERE text LIKE ?";
        return jdbcTemplate.query(sqlQuery, new String[]{"%"+text+"%"}, BeanPropertyRowMapper.newInstance(Message.class));
    }

    public boolean insert(Message message) {
        var sqlQuery = "INSERT INTO message VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, message.getId(), message.getText());
        return true;
    }

    public boolean update(Message message, Integer id) {
        jdbcTemplate.update("UPDATE message SET text = ? WHERE id = ?", message.getText(), id);
        return true;
    }

    public boolean delete(Integer id) {
        jdbcTemplate.update("DELETE FROM message WHERE id = ?", id);
        return true;
    }

}
```

## MessageResource.java
```java
@RestController
@RequestMapping("/message")
public class MessageResource {

    @Autowired
    MessageService messageService;

    @GetMapping
    public List<Message> getMessages(){
        return messageService.getMessages();
    }

    @GetMapping("/findbytext/{text}")
    public List<Message> getMessages(@PathVariable String text){
        return messageService.getMessageByText(text);
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable("id") Integer id){
        return messageService.getMessageById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Message message){
        return ResponseEntity.ok(messageService.insert(message));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Message message, @PathVariable("id") Integer id){
        return ResponseEntity.ok(messageService.update(message, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(messageService.delete(id));
    }
}
```

## Postman
><code>[postman/postman_collection.json](postman/postman_collection.json)</code>
