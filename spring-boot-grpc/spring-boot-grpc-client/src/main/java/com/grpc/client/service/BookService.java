package com.grpc.client.service;

import com.grpc.client.employee.BookRequest;
import com.grpc.client.employee.BookResponse;
import com.grpc.client.employee.BookServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    public void getBook() {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8090).usePlaintext().build();

        BookServiceGrpc.BookServiceBlockingStub stub = BookServiceGrpc.newBlockingStub(channel);

        BookResponse bookResponse = stub.getBook(BookRequest.newBuilder()
                .build());

        System.out.println(bookResponse);

        channel.shutdown();
    }
}
