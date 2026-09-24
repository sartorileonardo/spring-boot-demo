package com.example.demo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<String> handleNotificationException(NotificationException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
