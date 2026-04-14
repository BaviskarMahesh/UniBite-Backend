package com.unibite.unibit_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodItem extends BaseEntity{

    private String name;
    private double price;
    private boolean available;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="category_id")
    private FoodCategory foodCategory;
}
