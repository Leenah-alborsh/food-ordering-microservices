package com.foodapp.ordering_service.dto;

import lombok.Data;

@Data
public class DeliveryResponse {
    private Long id;
    private Long orderId;
    private Long customerId;
    private String deliveryAddress;
    private String courierName;
    private String deliveryStatus;
}
