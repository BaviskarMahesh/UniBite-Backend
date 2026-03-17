package com.unibite.unibit_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class BillResponse {
    private Long orderId;
    private String UserEmail;
    private List<Item> items;
    private double total;
    private String paymentStatus;
    private String utr;

    @Data
    @Builder
    public static class Item{
        private String name;
        private int quantity;
        private double price;
    }

}
