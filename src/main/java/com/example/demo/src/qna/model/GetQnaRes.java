package com.example.demo.src.qna.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetQnaRes {
    private int questionId;
    private String question;
    private String answer;
    private String category;
}
