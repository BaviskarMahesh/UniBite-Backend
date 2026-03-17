package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findByOrders_Id(Long orderId);
}
