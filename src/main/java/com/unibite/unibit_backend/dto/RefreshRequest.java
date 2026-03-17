package com.unibite.unibit_backend.dto;

import lombok.Data;

@Data
public class RefreshRequest {
    private String refreshToken;

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
