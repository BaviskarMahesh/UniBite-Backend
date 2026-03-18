package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.PaymentRequest;
import com.unibite.unibit_backend.entity.Payment;
import com.unibite.unibit_backend.enums.PaymentStatus;
import com.unibite.unibit_backend.repository.OrderRepository;
import com.unibite.unibit_backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment submitUtr(PaymentRequest request){
        if(request.getUtr() == null || request.getUtr().isEmpty()){
            throw new RuntimeException("UTR is required");
        }
        Payment payment = paymentRepository.findByOrders_Id(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setUtr(request.getUtr());
        return paymentRepository.save(payment);
    }

    public Payment verifyPayment(Long orderId){

        Payment payment = paymentRepository.findByOrders_Id(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.VERIFIED);
        return paymentRepository.save(payment);
    }
}


