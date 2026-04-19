package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.AddToCartRequest;
import com.unibite.unibit_backend.entity.CartItem;
import com.unibite.unibit_backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService service;
    @PostMapping("/add")
    public void add(@RequestBody @Valid AddToCartRequest request){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();
        service.addToCart(email, request);
    }
    @GetMapping
    public List<CartItem> get(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }
        String email = auth.getName();
        return service.getCart(email);
    }
}