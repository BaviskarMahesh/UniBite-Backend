package com.unibite.unibit_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Name is required")
private String name;
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
private String email;
    @Size(min=6,message = "Password must be at least 6 characters")
private String password;
private String phone;
}
