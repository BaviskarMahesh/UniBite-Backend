package com.unibite.unibit_backend.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createdAt=LocalDateTime.now();

}
