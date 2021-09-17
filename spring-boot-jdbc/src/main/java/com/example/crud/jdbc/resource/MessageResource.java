package com.example.crud.jdbc.resource;

import com.example.crud.jdbc.service.MessageService;
import com.example.crud.jdbc.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
