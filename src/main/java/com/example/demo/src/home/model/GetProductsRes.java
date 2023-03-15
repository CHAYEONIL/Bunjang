package com.example.demo.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductsRes {


    private int productId;
    private String price;
    private String title;
    private String isSagePay;
    private String tradeStatus;  //Available 인 경우만 넘기기
    private String status;  //Y인 경우만 넘기기
    private String imageUrl;

}
