package com.unibite.unibit_backend.dto;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FoodRequest {
    @NotBlank(message = "Food name is required")

    private String name;
    @Positive(message = "Price must be greater than 0")
    private Double price;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    private boolean available;
}
