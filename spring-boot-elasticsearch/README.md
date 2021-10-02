# spring-boot-elasticsearch

> Spring boot with Elasticsearch

## Run Elasticsearch with docker

1. Run container：`docker run -d --name es762 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2`
2. Stop container：`docker stop es762`
3. Start container：`docker start es762`


## ElasticSearchConfig.java

```java

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.cloud.demo.repository")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200", "localhost:9300")
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

}

```

## Product.java
```java

@Document(indexName = "productindex")
public class Product {

    @Id
    private Integer id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Integer, name = "count")
    private Integer count;

    @Field(type = FieldType.Auto, name = "price")
    private BigDecimal price;

    //...Getters and Setters and Constructor
}
```


## ProductRepository.java
```java
public interface ProductRepository extends ElasticsearchRepository<Product, Integer> {
    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameOrPrice(String name, BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
```

## ProductControllerTest.java
```java

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

```


