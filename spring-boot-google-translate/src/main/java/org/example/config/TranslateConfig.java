package org.example.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TranslateConfig {
    @Value("${translate.key}")
    private String key;
}
