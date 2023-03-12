package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchProductReq {
    private String title;
    private String category;
    private String location;
    private String productStatus;
    private String isChangable;
    private int quantity;
    private int price;
    private String isFreeShip;
    private String contents;
    private String isSagePay;

}
