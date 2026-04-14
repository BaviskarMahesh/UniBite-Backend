package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.*;
import com.unibite.unibit_backend.entity.RefreshToken;
import com.unibite.unibit_backend.entity.User;
import com.unibite.unibit_backend.enums.Role;
import com.unibite.unibit_backend.repository.RefreshTokenRepository;
import com.unibite.unibit_backend.repository.UserRepository;
import com.unibite.unibit_backend.security.Jwtutil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Jwtutil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    /// REGISTER
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        /// include role
        String accessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        saveRefreshToken(refreshToken, user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }

    // 🔥 LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 🔥 FIX: include role
        String accessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        saveRefreshToken(refreshToken, user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }

    // 🔥 SAVE TOKEN
    private void saveRefreshToken(String token, String email) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setEmail(email);
        refreshToken.setExpiryTime(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);
    }

    // 🔥 REFRESH TOKEN
    public AuthResponse refreshToken(RefreshRequest request) {

        RefreshToken token = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.isRevoked() || token.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired or revoked");
        }

        // 🔥 FIX: get user role
        User user = userRepository.findByEmail(token.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getEmail(),
                user.getRole().name()
        );

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(token.getToken())
                .email(token.getEmail())
                .build();
    }
}