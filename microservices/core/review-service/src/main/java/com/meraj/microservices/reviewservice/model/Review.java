package com.meraj.microservices.reviewservice.model;

import lombok.*;

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
