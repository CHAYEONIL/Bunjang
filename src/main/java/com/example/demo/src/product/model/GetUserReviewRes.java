package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReviewRes {

    private int productId;
    private int purchaseUserId;
    private String content;
    private Double score;
    private String createdAt;
    private String updatedAt;
}


