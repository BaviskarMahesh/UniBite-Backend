package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.*;
import com.unibite.unibit_backend.entity.User;
import com.unibite.unibit_backend.repository.UserRepository;
import com.unibite.unibit_backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

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

    @GetMapping("/me")
    public UserProfileResponse getCurrentUser(java.security.Principal principal) {

        /// ✅ SAFE CHECK
        if (principal == null || principal.getName() == null) {
            throw new RuntimeException("Unauthorized - No user found");
        }

        String email = principal.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserProfileResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}