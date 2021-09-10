package com.example.crud.jdbc.repository;

import com.example.crud.jdbc.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageRepository implements IMessageRepository {

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
