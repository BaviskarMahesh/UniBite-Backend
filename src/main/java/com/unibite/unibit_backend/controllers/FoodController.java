package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.FoodRequest;
import com.unibite.unibit_backend.dto.FoodUpdateRequest;
import com.unibite.unibit_backend.entity.FoodItem;
import com.unibite.unibit_backend.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {
    private final FoodItemService service;

    /// food items
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FoodItem create(@RequestBody @Valid FoodRequest request){
        return service.create(request);
    }
    /// getting all fooditems
    @GetMapping
    public List<FoodItem> getAll(){
        return service.getAll();
    }

    /// update fooditems
    @PutMapping("/{id}")
    public FoodItem update(
            @PathVariable @Valid Long id,
            @RequestBody @Valid FoodUpdateRequest request
            ){
        return service.update(id,request);
    }

    /// delete fooditems
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    /// toggle switch
    @PatchMapping("/{id}/status")
    public FoodItem toggle(@PathVariable Long id){
        return service.toggleAvailability(id);
    }
}
