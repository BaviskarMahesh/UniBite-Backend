package com.unibite.unibit_backend.entity;

import com.unibite.unibit_backend.enums.MenuType;
//import com.unibite.unibit_backend.enums.Menutype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="food_id")
    private FoodItem foodItem;
    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    private boolean available;
}

