package com.example.cloud.demo.respository;

import com.example.cloud.demo.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameOrPrice(String name, BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
