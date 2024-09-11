package com.grpc.client;

import com.grpc.client.service.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GrpcClientDemoJavaApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GrpcClientDemoJavaApplication.class, args);
		BookService bookService = context.getBean(BookService.class);
		bookService.getBook();
	}

}
