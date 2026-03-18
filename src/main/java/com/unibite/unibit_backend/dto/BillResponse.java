package com.unibite.unibit_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
public class BillResponse {
    private Long orderId;
    private String UserEmail;
    private List<Item> items;
    private double total;
    private String paymentStatus;
    private String utr;

    @Data
    @Builder
    @Getter
    @AllArgsConstructor
    public static class Item{
        private String name;
        private int quantity;
        private double totalPrice;
    }

}
