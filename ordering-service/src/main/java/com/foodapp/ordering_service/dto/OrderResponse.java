package com.foodapp.ordering_service.dto;

import com.foodapp.ordering_service.model.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private OrderEntity order;
    private CustomerResponse customer;
    private MenuItemResponse menuItem;
    private PaymentResponse payment;
    private KitchenOrderResponse kitchenOrder;
    private DeliveryResponse delivery;
    private String communicationType;
}
