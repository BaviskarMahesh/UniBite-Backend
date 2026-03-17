package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
}
