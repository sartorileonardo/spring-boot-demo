package com.example.cloud.demo.controller;

import com.example.cloud.demo.model.Product;
import com.example.cloud.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/saveAll")
    public ResponseEntity saveAll(@RequestBody List<Product> products){
        return ResponseEntity.ok(productService.saveAll(products));
    }

    @PostMapping
    public Product save(@RequestBody Product product){
        return productService.save(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity getProduct(@PathVariable Integer id){
        return productService.getProduct(id).map(p -> ResponseEntity.ok().body(p)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody Product product){
        Optional<Product> productSaved = productService.getProduct(id);
        if (!productSaved.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity deleteAll(){
        productService.deleteAll();
        return ResponseEntity.ok().build();
    }


    @GetMapping("/findByName/{name}")
    public ResponseEntity getProductByName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @GetMapping("/findByLikeName/{name}")
    public ResponseEntity getProductByLikeName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByLikeName(name));
    }

    @GetMapping("/findByContainsName/{name}")
    public ResponseEntity getProductByContainsName(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductByContainsName(name));
    }

    @GetMapping("findByNameOrPrice/{name}/{price}")
    public ResponseEntity getProductByNameOrPrice(@PathVariable String name, @PathVariable BigDecimal price){
        return ResponseEntity.ok(productService.getProductByNameOrPrice(name, price));
    }

    @GetMapping("/findByBetweenPrice/{minPrice}/{maxPrice}")
    public ResponseEntity getProductByBetweenPrice(@PathVariable BigDecimal minPrice, @PathVariable BigDecimal maxPrice){
        return ResponseEntity.ok(productService.getProductByBetweenPrice(minPrice, maxPrice));
    }

    @GetMapping("/findProductsWithNativeQuery/{name}")
    public ResponseEntity getProductsWithNativeQuery(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductsWithNativeQuery(name));
    }

    @GetMapping("/findProductsWithStringQuery/{name}")
    public ResponseEntity getProductsWithStringQuery(@PathVariable String name){
        return ResponseEntity.ok(productService.getProductsWithStringQuery(name));
    }

    @GetMapping("/findSuggestionsByName/{name}")
    public ResponseEntity getSuggestionsByName(@PathVariable String name){
        return ResponseEntity.ok(productService.getSuggestionsByName(name));
    }

}
