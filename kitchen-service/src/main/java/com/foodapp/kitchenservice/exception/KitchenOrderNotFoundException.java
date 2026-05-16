package com.foodapp.kitchenservice.exception;

public class KitchenOrderNotFoundException extends RuntimeException {

    public KitchenOrderNotFoundException(Long kitchenOrderId) {
        super("Kitchen order not found with id " + kitchenOrderId);
    }
}
