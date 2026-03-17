package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.PaymentRequest;
import com.unibite.unibit_backend.entity.Payment;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    public void submitUtr(PaymentRequest request){
        Payment payment=paymentRepository.findByOrders_Id(request.getOrderId())
                .orElseThrow();
        payment.setUtr(request.getUtr());
        payment.setStatus(PaymentStatus.PENDING);
        paymentRepository.save(payment);
    }
}


