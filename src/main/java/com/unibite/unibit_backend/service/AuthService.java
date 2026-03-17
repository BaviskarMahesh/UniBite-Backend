package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.AuthResponse;
import com.unibite.unibit_backend.dto.LoginRequest;
import com.unibite.unibit_backend.dto.RefreshRequest;
import com.unibite.unibit_backend.dto.RegisterRequest;
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
        //register new user
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

       String accessToken=jwtUtil.generateAccessToken(user.getEmail());
       String refreshToken=jwtUtil.generateRefreshToken(user.getEmail());
       saveRefreshToken(refreshToken,user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken((refreshToken))
                .email(user.getEmail())
                .build();
    }
        //login
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String refreshToken= jwtUtil.generateRefreshToken(user.getEmail());
        String accessToken= jwtUtil.generateAccessToken(user.getEmail());
        saveRefreshToken(refreshToken, user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }
    //saving token
    private void saveRefreshToken(String token,String email){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(token);
        refreshToken.setEmail(email);
        refreshToken.setExpiryTime(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);
    }

    public AuthResponse refreshToken(RefreshRequest request){
        RefreshToken token=refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(()->new RuntimeException("Invalid refresh token"));

        if(token.isRevoked()|| token.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expired or revoked");
        }
        String newAccessToken=jwtUtil.generateAccessToken(token.getEmail());
        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(token.getToken())
                .email(token.getEmail())
                .build();

    }

}