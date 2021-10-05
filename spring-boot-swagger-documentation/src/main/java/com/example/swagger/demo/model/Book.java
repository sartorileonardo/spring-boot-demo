package com.example.swagger.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value = "Book", description = "Model to represent a Book into system")
public class Book {
    @ApiModelProperty(value = "Book's ISBN", required = true)
    private String isbn;

    @ApiModelProperty(value = "Book's name", required = true)
    private String name;

    @ApiModelProperty(value = "Book's author", required = true)
    private String author;

    @ApiModelProperty(value = "Book's publication date")
    private LocalDateTime publicationDate;

    @ApiModelProperty(value = "Book's price")
    private BigDecimal price;

    public Book(String isbn, String name, String author, LocalDateTime publicationDate, BigDecimal price) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
        this.publicationDate = publicationDate;
        this.price = price;
    }

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
