package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostProductReq {
    private int productId;
    private int userId;
    private String title;
    private String category;
    private String location;
    private String productStatus;
    private String isChangable;
    private int quantity;
    private String price;
    private String isFreeShip;
    private String contents;
    private String isSagePay;
    private List<PostProductImageReq> productImages;


}
