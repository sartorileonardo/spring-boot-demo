package com.example.cloud.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void getProducts() {
        assertNotNull(productService.getProducts());
    }

    @Test
    void getProductByName() {
        assertNotNull(productService.getProductByName("TV"));
    }

    @Test
    void getProductByLikeName() {
        assertEquals("TV", productService.getProductByLikeName("TV").get(0).getName());
    }

    @Test
    void getProductByContainsName() {
        assertEquals("TV", productService.getProductByContainsName("TV").get(0).getName());
    }

    @Test
    void getProductByNameOrPrice() {
        assertEquals("TV", productService.getProductByNameOrPrice("TV", new BigDecimal("1200.50")).get(0).getName());
    }

    @Test
    void getProductByBetweenPrice() {
        BigDecimal minPrice = new BigDecimal("1300.00");
        BigDecimal maxPrice = new BigDecimal("1900.00");
        assertEquals("TV", productService.getProductByBetweenPrice(minPrice, maxPrice).get(0).getName());
    }

    @Test
    void getProductsWithNativeQuery() {
        assertEquals(1, productService.getProductsWithNativeQuery("tv").getTotalHits());
    }

    @Test
    void getProductsWithStringQuery() {
        assertEquals(1, productService.getProductsWithStringQuery("tv").getTotalHits());
    }

    @Test
    void getSuggestionsByName() {
        assertEquals("TV", productService.getSuggestionsByName("tv").get(0));
    }

    @Test
    void getProduct() {
        assertEquals(1, productService.getProduct(1).get().getId());
    }

}