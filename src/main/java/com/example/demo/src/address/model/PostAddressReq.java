package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostAddressReq {
    private String name;
    private String phoneNum;
    private String addressName;
    private String addressDetail;
    private String addressBase;
}
