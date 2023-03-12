package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Review {
    private int reviewIdx;
    private int storeUserId;
    private int purchaseUserId;
    private double score;
    private String content;
    private String status;
}
