package com.example.demo.src.address.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAddressRes {
    private int addressId;
    private String name;
    private String phoneNum;
    private String addressName;
    private String addressDetail;
    private String addressBase;
}
