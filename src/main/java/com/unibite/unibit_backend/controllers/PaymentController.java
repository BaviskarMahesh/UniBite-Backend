package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.PaymentRequest;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.repository.PaymentRepository;
import com.unibite.unibit_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/submit")
    public void submit(@RequestBody PaymentRequest request){
        paymentService.submitUtr(request);
    }
}
