package com.unibite.unibit_backend.repository;

import com.unibite.unibit_backend.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    List<FoodItem> findByFoodCategory_Id(Long id);
}
