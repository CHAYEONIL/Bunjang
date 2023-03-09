package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {
    private int userIdx;
    private String name;
    private String userNickName;
    private String profileImageUrl;
    private String email;
    private String password;
    private String phoneNum;
    private String content;
    private String status;
}
