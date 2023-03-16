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
    private String title;
    private int price;
    private String userNickName;
    private String ImageUrl;
    private String time;
}
