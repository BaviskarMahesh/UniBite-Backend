package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.OrderItem;
import com.unibite.unibit_backend.entity.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrders(Orders orders);
}
