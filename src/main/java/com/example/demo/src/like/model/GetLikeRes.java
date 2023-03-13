package com.example.demo.src.like.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetLikeRes {
    private int likeId;
    private int userId;
    private int productId;
}
