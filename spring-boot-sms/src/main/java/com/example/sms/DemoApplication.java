package com.example.sms;

import com.example.sms.config.PropertiesLoader;
import com.twilio.Twilio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public PropertiesLoader getLoaderProperties(){
		return new PropertiesLoader();
	}

	@Bean
	public void initializeTwilio(){
		PropertiesLoader config = getLoaderProperties();
		Twilio.init(config.getAccountSid(), config.getAuthToken());
	}

}
