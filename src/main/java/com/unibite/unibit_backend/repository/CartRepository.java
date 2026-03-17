package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserEmail(String email);
}
