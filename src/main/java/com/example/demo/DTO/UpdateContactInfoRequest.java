package com.example.demo.DTO;

import lombok.Getter;

@Getter
public class UpdateContactInfoRequest {
    private String fullName;
    private String phone;
    private String email;
    private String city;
    private String country;
    private String birthDate;
}
