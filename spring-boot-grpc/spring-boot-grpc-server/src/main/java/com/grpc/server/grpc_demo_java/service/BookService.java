package com.grpc.server.grpc_demo_java.service;

/*import com.javainuse.constants.Type;
import com.javainuse.employee.BookRequest;
import com.javainuse.employee.BookResponse;
import com.javainuse.employee.BookServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.util.NumberUtils;

import java.util.UUID;*/

import com.grpc.server.grpc_demo_java.constants.Type;
import com.grpc.server.grpc_demo_java.employee.BookRequest;
import com.grpc.server.grpc_demo_java.employee.BookResponse;
import com.grpc.server.grpc_demo_java.employee.BookServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@GrpcService
public class BookService extends BookServiceGrpc.BookServiceImplBase {

    /**
     * Unary operation to get the book based on book id
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getBook(BookRequest request, StreamObserver<BookResponse> responseObserver) {

        // We have mocked the employee data.
        // In real world this should be fetched from database or from some other source
        BookResponse empResp = BookResponse.newBuilder()
                .setBookId(UUID.randomUUID().toString())
                .setName("Endurance: Shackleton's Incredible Voyage")
                .setPrice(35)
                .setType(Type.FANTASY)
                .build();

        // set the response object
        responseObserver.onNext(empResp);

        // mark process is completed
        responseObserver.onCompleted();
    }
}

