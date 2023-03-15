package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewRes {
    private int reviewId;
    private int storeUserId;
    private int purchaseUserId;
    private double score;
    private String content;
}
