package com.unibite.unibit_backend.controllers;


import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/place")
    public BillResponse place(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.placeOrder(email);
    }
}
