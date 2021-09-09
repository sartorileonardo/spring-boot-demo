package com.example.crud.jdbc.service;

import com.example.crud.jdbc.vo.Message;

import java.util.List;

public interface IMessageService {
    List<Message> getMessages();
    Message getMessageById(Integer id);
    List<Message> getMessageByText(String text);
    boolean insert(Message message);
    boolean update(Message message, Integer id);
    boolean delete(Integer id);
}
