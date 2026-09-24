package com.example.demo.controller;

import com.example.demo.service.NotificationService;
import com.example.demo.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Test
    void shouldDelegateStreamingToService() throws Exception {
        when(notificationService.streamEvents()).thenReturn(new SseEmitter());

        mockMvc.perform(get("/api/sse/stream").accept(MediaType.TEXT_EVENT_STREAM))
                .andExpect(request().asyncStarted());

        verify(notificationService).streamEvents();
    }

    @Test
    void shouldReturnExceptionMessageAndStatusFromGlobalAdvice() throws Exception {
        when(notificationService.streamEvents())
                .thenThrow(new NotificationException("notificações indisponíveis",
                        org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE));

        mockMvc.perform(get("/api/sse/stream"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(content().string("notificações indisponíveis"));
    }
}
