package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterUserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
