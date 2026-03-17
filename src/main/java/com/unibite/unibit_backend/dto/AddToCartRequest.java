package com.unibite.unibit_backend.dto;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long foodId;
    private int quantity;
}
