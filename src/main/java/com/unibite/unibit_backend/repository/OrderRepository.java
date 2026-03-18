package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Orders;
import com.unibite.unibit_backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByStatus(OrderStatus status);
    List<Orders> findByCreatedAtBetween(LocalDateTime start , LocalDateTime end);
}
