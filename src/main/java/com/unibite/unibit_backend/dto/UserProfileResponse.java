package com.unibite.unibit_backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String name;
    private String email;
    private String phone;
}