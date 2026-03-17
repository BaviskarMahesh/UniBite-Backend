package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
}
