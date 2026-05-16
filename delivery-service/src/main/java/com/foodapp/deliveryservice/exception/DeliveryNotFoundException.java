package com.foodapp.deliveryservice.exception;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(Long deliveryId) {
        super("Delivery not found with id " + deliveryId);
    }
}
