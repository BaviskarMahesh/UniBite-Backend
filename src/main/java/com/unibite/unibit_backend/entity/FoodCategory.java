package com.unibite.unibit_backend.entity;


import jakarta.persistence.Entity;

import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FoodCategory extends BaseEntity{

    private String name;
}
