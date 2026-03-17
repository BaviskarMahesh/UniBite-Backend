package com.unibite.unibit_backend.entity;

import com.unibite.unibit_backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
@Id
@GeneratedValue
    private Long id;
@OneToOne
@JoinColumn(name="orders_id")
    private Orders orders;
private String utr;
@Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
