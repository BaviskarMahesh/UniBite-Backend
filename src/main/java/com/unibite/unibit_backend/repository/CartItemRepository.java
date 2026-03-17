package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Cart;
import com.unibite.unibit_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCart(Cart cart);
}
