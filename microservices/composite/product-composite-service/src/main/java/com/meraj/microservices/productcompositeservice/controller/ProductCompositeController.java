package com.meraj.microservices.productcompositeservice.controller;

import com.meraj.microservices.productcompositeservice.model.Product;
import com.meraj.microservices.productcompositeservice.model.ProductAggregated;
import com.meraj.microservices.productcompositeservice.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductCompositeController {
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/productComposite/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable String productId) {
        ResponseEntity<List<Review>> reviewResponse =
                restTemplate.exchange("http://localhost:8001/review/" + productId,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                        });
        List<Review> review = reviewResponse.getBody();
        Product product =  restTemplate.getForObject("http://localhost:8000/product/" + productId, Product.class);

        return new ResponseEntity<>(new ProductAggregated(product, review), HttpStatus.OK);
    }
}