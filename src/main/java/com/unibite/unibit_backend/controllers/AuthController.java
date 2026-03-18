 package com.unibite.unibit_backend.controllers;
import com.unibite.unibit_backend.dto.AuthResponse;
import com.unibite.unibit_backend.dto.LoginRequest;
import com.unibite.unibit_backend.dto.RefreshRequest;
import com.unibite.unibit_backend.dto.RegisterRequest;
import com.unibite.unibit_backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {

        return authService.login(request);
    }
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request){
        return authService.refreshToken(request);
    }
}