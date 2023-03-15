package com.example.demo.src.notice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetNoticeRes {

    private int id;
    private String content;
    private String updatedAt;

}
