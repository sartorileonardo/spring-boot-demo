package com.example.crud.jdbc.service;

import com.example.crud.jdbc.repository.IMessageRepository;
import com.example.crud.jdbc.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService{

    @Autowired
    IMessageRepository messageRepository;

    public List<Message> getMessages() {
        return messageRepository.getMessages();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.getMessageById(id);
    }

    public List<Message> getMessageByText(String text) {
        return messageRepository.getMessageByText(text);
    }

    public boolean insert(Message message) {
        return messageRepository.insert(message);
    }

    public boolean update(Message message, Integer id) {
        return messageRepository.update(message, id);
    }

    public boolean delete(Integer id) {
        return messageRepository.delete(id);
    }

}
