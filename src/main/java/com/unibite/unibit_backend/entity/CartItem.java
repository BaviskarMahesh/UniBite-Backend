package com.unibite.unibit_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private FoodItem foodItem;
    private int quantity;
}
