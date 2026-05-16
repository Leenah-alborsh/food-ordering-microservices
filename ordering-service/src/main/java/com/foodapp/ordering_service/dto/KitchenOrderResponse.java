package com.foodapp.ordering_service.dto;

import lombok.Data;

@Data
public class KitchenOrderResponse {
    private Long id;
    private Long orderId;
    private String itemName;
    private Integer quantity;
    private String prepStatus;
    private String notes;
}
