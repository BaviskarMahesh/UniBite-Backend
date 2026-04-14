package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.entity.User;
import com.unibite.unibit_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    // ADMIN ONLY
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}