package com.example.demo.service;

import com.example.demo.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NotificationServiceTest {

    @Test
    void shouldSendConfiguredNotificationsAndCompleteEmitter() throws IOException {
        TaskExecutor executor = Runnable::run;
        NotificationService service = new NotificationService(executor, 2, Duration.ZERO);
        SseEmitter emitter = mock(SseEmitter.class);

        service.sendEvents(emitter);

        verify(emitter, times(2)).send(any(SseEmitter.SseEventBuilder.class));
        verify(emitter).complete();
    }

    @Test
    void shouldRejectInvalidEventCount() {
        NotificationException exception = assertThrows(NotificationException.class,
                () -> new NotificationService(Runnable::run, 0, Duration.ZERO));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void shouldRejectNegativeEventDelay() {
        NotificationException exception = assertThrows(NotificationException.class,
                () -> new NotificationService(Runnable::run, 1, Duration.ofSeconds(-1)));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void shouldCompleteWithNotificationExceptionWhenSendingFails() throws IOException {
        NotificationService service = new NotificationService(Runnable::run, 1, Duration.ZERO);
        SseEmitter emitter = mock(SseEmitter.class);
        doThrow(new IOException("send failed"))
                .when(emitter).send(any(SseEmitter.SseEventBuilder.class));

        service.sendEvents(emitter);

        verify(emitter).completeWithError(any(NotificationException.class));
    }

}
