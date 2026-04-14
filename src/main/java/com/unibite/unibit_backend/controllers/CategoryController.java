package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.CategoryRequest;
import com.unibite.unibit_backend.entity.FoodCategory;
import com.unibite.unibit_backend.service.FoodCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final FoodCategoryService service;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public FoodCategory create(@RequestBody CategoryRequest request){
        return service.create(request);
    }
    @GetMapping
    public List<FoodCategory> getAll(){
        return service.getAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
