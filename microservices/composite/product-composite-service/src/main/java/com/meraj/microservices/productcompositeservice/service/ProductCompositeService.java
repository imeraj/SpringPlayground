package com.meraj.microservices.productcompositeservice.service;

import com.meraj.microservices.productcompositeservice.model.Product;
import com.meraj.microservices.productcompositeservice.model.Review;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductCompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeService.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "defaultReview")
    public ResponseEntity<List<Review>> getReview(String productId) {
        ResponseEntity<List<Review>> reviewResponse =
                restTemplate.exchange("http://review-service/review/" + productId,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
                        });
        return reviewResponse;
    }

    public ResponseEntity<List<Review>> defaultReview(String productId) {
        LOG.warn("Using fallback method for review-service");
        List<Review> defaultReview = new ArrayList<>();
        return new ResponseEntity<>(defaultReview, HttpStatus.BAD_GATEWAY);
    }

    public Product getProduct(String productId) {
        return restTemplate.getForObject("http://product-service/product/" + productId, Product.class);
    }
}
