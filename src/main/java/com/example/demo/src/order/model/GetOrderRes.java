package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {

    //Product
    private int productId;
    private String productPrice;
    private String productTitle;
    private String sellerName;

    //Order
    private String orderTime;
    private String orderStatus;
    private int orderTotalPrice;
    private int orderProductPrice;
    private int deliveryFee;


    //Address
    private int addressId;
    private String buyerName;
    private String phoneNum;
    private String addressName;
    private String addressDetail;
    private String orderDeliveryReq;

}
