package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    @Autowired
    private CacheManager cacheManager;

    @Scheduled(fixedRate = 300000000)
    public void cleanCache(){
        cacheManager.getCache("text").clear();
    }
}
