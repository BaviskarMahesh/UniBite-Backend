package com.unibite.unibit_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class FoodUpdateRequest {




    private String name;
    private Double price;
    private Boolean available;
    private Long categoryId;
}
