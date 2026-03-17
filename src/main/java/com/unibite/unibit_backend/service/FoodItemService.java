package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.FoodRequest;
import com.unibite.unibit_backend.dto.FoodUpdateRequest;
import com.unibite.unibit_backend.entity.FoodCategory;
import com.unibite.unibit_backend.entity.FoodItem;
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
    public FoodItem create(FoodRequest request){
        FoodCategory category=foodCategoryRepository.findById(request.getCategoryId())
                .orElseThrow();
        return foodItemRepository.save(FoodItem.builder()
                .name(request.getName())
                .price(request.getPrice())
                .available(request.isAvailable())
                .foodCategory(category)
                .build());


    }
    public List<FoodItem> getAll(){
        return foodItemRepository.findAll();
    }
    /// update food items
    public FoodItem update(Long id, FoodUpdateRequest request){
        FoodItem foodItem=foodItemRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Food not Found"));
        if(request.getName()!=null){
            foodItem.setName(request.getName());
        }
        if(request.getPrice()!=0){
            foodItem.setPrice(request.getPrice());
        }
        if(request.getAvailable()!=null){
            foodItem.setAvailable(request.getAvailable());
        }

        if(request.getCategoryId()!=null){
            FoodCategory foodCategory=foodCategoryRepository.findById(request.getCategoryId())
                    .orElseThrow();
            foodItem.setFoodCategory(foodCategory);
        }
        return foodItemRepository.save(foodItem);
    }

    /// delete fooditem
    public void delete(Long id){
        FoodItem foodItem=foodItemRepository.findById(id).orElseThrow(()->new RuntimeException("Food not found"));
        foodItemRepository.delete(foodItem);
    }
    ///TOGGLE AVAILABILITY
    public FoodItem toggleAvailability(Long id){

        FoodItem food = foodItemRepository.findById(id)
                .orElseThrow();

        food.setAvailable(!food.isAvailable());

        return foodItemRepository.save(food);
    }
}
