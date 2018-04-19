package com.meraj.microservices.productservice.Controller;

import com.meraj.microservices.productservice.model.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @RequestMapping(method = RequestMethod.GET, value="{productId}")
    public Product getProduct(@PathVariable int productId) {
        return new Product(productId, "product-name", 12.3);
    }
}
