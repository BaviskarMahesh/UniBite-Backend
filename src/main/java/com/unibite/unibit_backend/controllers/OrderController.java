package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.entity.Orders;
import com.unibite.unibit_backend.enums.OrderStatus;
import com.unibite.unibit_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // PLACE ORDER (USER)
    @PostMapping("/place")
    public BillResponse place(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return orderService.placeOrder(email);
    }

    // ADMIN: VIEW ALL ORDERS
    @GetMapping("/all")
    public List<Orders> getAllOrders(){
        return orderService.getAllOrders();
    }

    // ADMIN: UPDATE STATUS
    @PutMapping("/{orderId}/status")
    public Orders updateStatus(@PathVariable Long orderId,
                               @RequestParam OrderStatus status){
        return orderService.updateStatus(orderId, status);
    }

    // ADMIN: FILTER BY STATUS
    @GetMapping("/status")
    public List<Orders> getByStatus(@RequestParam OrderStatus status){
        return orderService.getByStatus(status);
    }

    // ADMIN: DAILY SALES
    @GetMapping("/sales/today")
    public double getTodaySales(){
        return orderService.getTodaySales();
    }
}