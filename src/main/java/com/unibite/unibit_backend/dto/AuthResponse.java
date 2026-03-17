package com.unibite.unibit_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String refreshToken;
    private String accessToken;
    private String email;
}
