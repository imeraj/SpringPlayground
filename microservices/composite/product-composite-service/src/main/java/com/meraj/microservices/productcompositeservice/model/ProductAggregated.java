package com.meraj.microservices.productcompositeservice.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAggregated {
    private Product product;
    private List<Review> review;
}
