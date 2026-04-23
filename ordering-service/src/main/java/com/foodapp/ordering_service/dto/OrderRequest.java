package com.foodapp.ordering_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long customerId;
    private String productName;
    private Integer quantity;
}
