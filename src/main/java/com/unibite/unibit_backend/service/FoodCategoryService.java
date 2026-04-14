package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.CategoryRequest;
import com.unibite.unibit_backend.entity.FoodCategory;
import com.unibite.unibit_backend.repository.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodCategoryService {

    private final FoodCategoryRepository repository;
    public FoodCategory create(CategoryRequest request){
        return repository.save(FoodCategory.builder()
                .name(request.getName())
                .build());
    }
    public List<FoodCategory> getAll(){
        return repository.findAll();
    }
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Category not found");
        }
        repository.deleteById(id);
    }
}
