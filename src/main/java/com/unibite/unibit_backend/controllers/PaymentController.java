package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.PaymentRequest;
import com.unibite.unibit_backend.entity.Payment;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.repository.PaymentRepository;
import com.unibite.unibit_backend.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/submit")
    public void submit(@RequestBody @Valid PaymentRequest request){
        paymentService.submitUtr(request);
    }

    @PutMapping("/verify/{orderId}")
    public Payment verify(@PathVariable Long orderId){
        return paymentService.verifyPayment(orderId);
    }
}
