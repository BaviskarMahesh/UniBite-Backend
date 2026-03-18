package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.FoodRequest;
import com.unibite.unibit_backend.dto.FoodUpdateRequest;
import com.unibite.unibit_backend.entity.FoodCategory;
import com.unibite.unibit_backend.entity.FoodItem;

import com.unibite.unibit_backend.exceptions.BadRequestException;
import com.unibite.unibit_backend.exceptions.ResourceNotFoundException;
import com.unibite.unibit_backend.repository.FoodCategoryRepository;
import com.unibite.unibit_backend.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final FoodItemRepository foodItemRepository;

    /// CREATE FOOD
    public FoodItem create(FoodRequest request){

        if(request.getName() == null || request.getName().trim().isEmpty()){
            throw new BadRequestException("Food name is required");
        }

        if(request.getPrice() <= 0){
            throw new BadRequestException("Price must be greater than 0");
        }

        FoodCategory category = foodCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return foodItemRepository.save(
                FoodItem.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                        .available(request.isAvailable())
                        .foodCategory(category)
                        .build()
        );
    }

    /// GET ALL
    public List<FoodItem> getAll(){
        return foodItemRepository.findAll();
    }

    // ✅ UPDATE FOOD
    public FoodItem update(Long id, FoodUpdateRequest request){

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        if(request.getName() != null && !request.getName().trim().isEmpty()){
            foodItem.setName(request.getName());
        }

        if(request.getPrice() != null){
            if(request.getPrice() <= 0){
                throw new BadRequestException("Price must be greater than 0");
            }
            foodItem.setPrice(request.getPrice());
        }

        if(request.getAvailable() != null){
            foodItem.setAvailable(request.getAvailable());
        }

        if(request.getCategoryId() != null){
            FoodCategory category = foodCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            foodItem.setFoodCategory(category);
        }

        return foodItemRepository.save(foodItem);
    }

    /// DELETE FOOD
    public void delete(Long id){

        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        foodItemRepository.delete(foodItem);
    }

    /// TOGGLE AVAILABILITY
    public FoodItem toggleAvailability(Long id){

        FoodItem food = foodItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        food.setAvailable(!food.isAvailable());

        return foodItemRepository.save(food);
    }
}