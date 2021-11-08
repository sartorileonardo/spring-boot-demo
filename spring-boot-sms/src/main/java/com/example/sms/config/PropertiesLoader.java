package com.example.sms.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class PropertiesLoader {
    @Value("${account.sid}")
    private String accountSid;
    @Value("${auth.token}")
    private String authToken;
}
