package com.meraj.microservices.productservice.controller;

import com.meraj.microservices.productservice.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @RequestMapping(method = RequestMethod.GET, value="{productId}")
    public Product getProduct(@PathVariable String productId) {
        return new Product(productId, "car", 12.3);
    }
}
