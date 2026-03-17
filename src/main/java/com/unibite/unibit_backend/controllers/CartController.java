package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.AddToCartRequest;
import com.unibite.unibit_backend.entity.CartItem;
import com.unibite.unibit_backend.service.CartService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;
    @PostMapping("/add")
    public void add(@RequestBody AddToCartRequest request){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        service.addToCart(email,request);
    }
    @GetMapping
    public List<CartItem> get(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        return  service.getCart(email);
    }
}
