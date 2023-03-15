package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMyPageRes {
    private int userId;
    private String profileImageUrl;
    private String userNickName;
    private String content;
    private double scoreAvg;
    private String openDay;
    private String userStatusCheck;
    private String status;
}
