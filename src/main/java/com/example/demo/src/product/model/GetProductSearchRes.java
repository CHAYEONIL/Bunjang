package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductSearchRes {
    private int productId;
    private String title;
    private String price;
    private String isSagePay;
    private String imageUrl;
}