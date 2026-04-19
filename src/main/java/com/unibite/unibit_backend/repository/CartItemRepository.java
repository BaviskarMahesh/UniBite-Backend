package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Cart;
import com.unibite.unibit_backend.entity.CartItem;
import com.unibite.unibit_backend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndFoodItem(Cart cart, FoodItem foodItem);
}
