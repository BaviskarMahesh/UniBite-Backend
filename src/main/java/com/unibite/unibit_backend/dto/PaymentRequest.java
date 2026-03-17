package com.unibite.unibit_backend.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private String utr;
}
