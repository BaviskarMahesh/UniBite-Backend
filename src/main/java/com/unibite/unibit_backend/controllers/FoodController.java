package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.FoodRequest;
import com.unibite.unibit_backend.dto.FoodUpdateRequest;
import com.unibite.unibit_backend.entity.FoodItem;
import com.unibite.unibit_backend.repository.FoodItemRepository;
import com.unibite.unibit_backend.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodItemService service;
    private final FoodItemRepository foodItemRepository;

    /// CREATE
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FoodItem create(@RequestBody @Valid FoodRequest request){
        return service.create(request);
    }

    /// GET ALL (PUBLIC)
    @GetMapping
    public List<FoodItem> getAll(){
        return service.getAll();
    }

    /// UPDATE
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public FoodItem update(@PathVariable Long id,
                           @RequestBody @Valid FoodUpdateRequest request){
        return service.update(id, request);
    }

    /// DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    /// TOGGLE
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public FoodItem toggle(@PathVariable Long id){
        return service.toggleAvailability(id);
    }

    /// SEARCH
    @GetMapping("/search")
    public List<FoodItem> search(@RequestParam String name){
        return foodItemRepository.findByNameContainingIgnoreCase(name);
    }

    /// CATEGORY
    @GetMapping("/category/{id}")
    public List<FoodItem> byCategory(@PathVariable Long id){
        return foodItemRepository.findByFoodCategory_Id(id);
    }
}