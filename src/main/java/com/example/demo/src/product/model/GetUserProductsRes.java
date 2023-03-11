package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserProductsRes {

    private int productId;
    private int userId;
    private String title;
    private String price;
    private int like;
    private int likeId;
    private List<GetProductsImgRes> getProductsImgRes;
}
