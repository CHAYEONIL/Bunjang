package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductRes {
    private int productId;
    private int userId;
    private String title;
    private int price;
    private String tradeStatus;
    private String imageUrl;
}
