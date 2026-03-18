package com.unibite.unibit_backend.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity
{

    @ManyToOne
    private Orders orders;
    @ManyToOne
    private FoodItem foodItem;
    private int quantity;
    private double price;
}
