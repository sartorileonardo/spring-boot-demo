package com.r2dbc.demo.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class Config {
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory conn){
        ConnectionFactoryInitializer init = new ConnectionFactoryInitializer();
        init.setConnectionFactory(conn);
        init.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return init;
    }
}
