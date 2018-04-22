package com.meraj.microservices.productcompositeservice.controller;

import com.meraj.microservices.productcompositeservice.model.Product;
import com.meraj.microservices.productcompositeservice.model.ProductAggregated;
import com.meraj.microservices.productcompositeservice.model.Review;
import com.meraj.microservices.productcompositeservice.service.ProductCompositeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductCompositeController {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductCompositeService productCompositeService;

    @RequestMapping("/productComposite/{productId}")
    public ResponseEntity<ProductAggregated> getProduct(@PathVariable String productId) {
        List<Review> review = productCompositeService.getReview(productId).getBody();
        Product product = productCompositeService.getProduct(productId);

        return new ResponseEntity<>(new ProductAggregated(product, review), HttpStatus.OK);
    }
}