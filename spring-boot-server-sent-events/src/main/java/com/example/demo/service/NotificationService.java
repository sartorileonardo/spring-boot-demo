package com.example.demo.service;

import com.example.demo.exceptions.NotificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

@Service
public class NotificationService {

    private final TaskExecutor taskExecutor;
    private final int eventCount;
    private final Duration eventDelay;

    public NotificationService(
            TaskExecutor taskExecutor,
            @Value("${sse.event-count:10}") int eventCount,
            @Value("${sse.event-delay:2s}") Duration eventDelay) {
        if (eventCount < 1) {
            throw new NotificationException(
                    "sse.event-count must be greater than zero", HttpStatus.BAD_REQUEST);
        }
        if (eventDelay.isNegative()) {
            throw new NotificationException(
                    "sse.event-delay must not be negative", HttpStatus.BAD_REQUEST);
        }
        this.taskExecutor = taskExecutor;
        this.eventCount = eventCount;
        this.eventDelay = eventDelay;
    }

    public SseEmitter streamEvents() {
        SseEmitter emitter = new SseEmitter(0L);
        taskExecutor.execute(() -> sendEvents(emitter));
        return emitter;
    }

    void sendEvents(SseEmitter emitter) {
        try {
            for (int i = 1; i <= eventCount; i++) {
                String data = "Mensagem #" + i + " recebida às " + LocalTime.now();

                emitter.send(SseEmitter.event()
                        .id(String.valueOf(i))
                        .name("notificacao")
                        .data(data));

                if (i < eventCount) {
                    Thread.sleep(eventDelay.toMillis());
                }
            }
            emitter.complete();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            emitter.completeWithError(new NotificationException(
                    "A transmissão das notificações foi interrompida", HttpStatus.INTERNAL_SERVER_ERROR, e));
        } catch (IOException | RuntimeException e) {
            emitter.completeWithError(new NotificationException(
                    "Não foi possível transmitir as notificações", HttpStatus.INTERNAL_SERVER_ERROR, e));
        }
    }
}
