package com.unibite.unibit_backend.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItem extends BaseEntity{

    @ManyToOne
    private Cart cart;
    @ManyToOne
    private FoodItem foodItem;
    private int quantity;
}
