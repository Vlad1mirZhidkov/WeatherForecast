package com.example.demo.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WeatherUserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String city;
    private String country;
    private String birthDate;
}
