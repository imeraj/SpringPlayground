package com.meraj.microservices.productcompositeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private String productId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;
}