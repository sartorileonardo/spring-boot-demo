package com.example.swagger.demo.controller;

import com.example.swagger.demo.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Books API")
@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Getting all books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Getting all books"),
            @ApiResponse(code = 400, message = "Business error"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public ResponseEntity getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }
}
