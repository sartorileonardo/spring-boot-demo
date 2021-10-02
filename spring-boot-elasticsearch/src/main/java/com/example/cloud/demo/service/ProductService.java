package com.example.cloud.demo.service;

import com.example.cloud.demo.model.Product;
import com.example.cloud.demo.respository.ProductRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static final String PRODUCT_INDEX = "productindex";

    public Iterable<Product> saveAll(List<Product> products){
        return productRepository.saveAll(products);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Optional<Product> getProduct(Integer id){
        return productRepository.findById(id);
    }

    public Iterable<Product> getProducts(){
        return productRepository.findAll();
    }

    public void delete(Integer id){
        productRepository.deleteById(id);
    }

    public void deleteAll(){
        productRepository.deleteAll();
    }


    public List<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> getProductByLikeName(String name){
        return productRepository.findByNameLike(name);
    }

    public List<Product> getProductByContainsName(String name){
        return productRepository.findByNameContaining(name);
    }

    public List<Product> getProductByNameOrPrice(String name, BigDecimal price){
        return productRepository.findByNameOrPrice(name, price);
    }

    public List<Product> getProductByBetweenPrice(BigDecimal minPrice, BigDecimal maxPrice){
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public SearchHits<Product> getProductsWithNativeQuery(String name){
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", name);
        Query searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        SearchHits<Product> productHists = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        return productHists;
    }

    public SearchHits<Product> getProductsWithStringQuery(String name) {
        Query searchQuery = new StringQuery("{\"match\":{\"name\":{\"query\":\""+ name + "\"}}}\"");
        SearchHits<Product> productHists = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));
        return productHists;
    }

    public List<String> getSuggestionsByName(String name) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("name", name+"*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<Product> searchSuggestions = elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of(PRODUCT_INDEX));

        List<String> suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit->{
            suggestions.add(searchHit.getContent().getName());
        });
        return suggestions;
    }
}
