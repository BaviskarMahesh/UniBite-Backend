package com.unibite.unibit_backend.dto;

import lombok.Data;

@Data
public class FoodRequest {
    private String name;
    private double price;
    private Long categoryId;
    private boolean available;
}
