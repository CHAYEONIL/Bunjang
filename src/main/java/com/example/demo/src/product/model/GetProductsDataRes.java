package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetProductsDataRes {

    private int productId;
    private int userId;
    private String title;
    private String category;
    private String location;
    private String productStatus;  //상품상태
    private String isChangable;  //교환 가능 여부
    private int quantity;  //수량
    private String price;  //가격
    private String isFreeShip; //배송비 포함 여부

    private String contents;
    private String isSagePay;  //안전 결제 선택 여부
    private String tradeStatus;  //판매 상태
    private String productUpdatedAtTime;

    private List<GetProductsLikeRes> likes;
    private List<GetProductsImgRes> imgs;

}
