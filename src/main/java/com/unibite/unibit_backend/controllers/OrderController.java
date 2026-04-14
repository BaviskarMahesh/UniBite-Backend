package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.BillResponse;
import com.unibite.unibit_backend.entity.Orders;
import com.unibite.unibit_backend.enums.OrderStatus;
import com.unibite.unibit_backend.service.BillService;
import com.unibite.unibit_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BillService billService;


    @PostMapping("/place")
    public ResponseEntity<BillResponse> place() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(orderService.placeOrder(email));
    }


    @GetMapping
    public ResponseEntity<Page<Orders>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(orderService.getOrders(page, size));
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<Orders> updateStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }


    @GetMapping("/status")
    public ResponseEntity<?> getByStatus(@RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.getByStatus(status));
    }


    @GetMapping("/sales/today")
    public ResponseEntity<Double> getTodaySales() {
        return ResponseEntity.ok(orderService.getTodaySales());
    }


    @GetMapping("/{id}/bill")
    public ResponseEntity<BillResponse> getBill(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getBill(id));
    }


    @GetMapping("/{id}/bill/pdf")
    public ResponseEntity<byte[]> downloadBill(@PathVariable Long id) {
        Orders orders = orderService.getById(id);
        byte[] pdf = billService.generateBill(orders);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=bill.pdf")
                .header("Content-Type", "application/pdf")
                .body(pdf);
    }
}