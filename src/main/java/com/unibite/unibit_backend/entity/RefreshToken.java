package com.unibite.unibit_backend.entity;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RefreshToken extends BaseEntity{

    private String token;
    private String email;
    private LocalDateTime expiryTime;
    private boolean revoked=false;
}
