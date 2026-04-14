package com.unibite.unibit_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unibite.unibit_backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders extends BaseEntity {

    private String userEmail;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderItem> items;
}
