package com.foodapp.deliveryservice.service;

import com.foodapp.deliveryservice.exception.DeliveryNotFoundException;
import com.foodapp.deliveryservice.model.Delivery;
import com.foodapp.deliveryservice.repository.DeliveryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new DeliveryNotFoundException(id));
    }

    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DeliveryNotFoundException(orderId));
    }

    public Delivery createDelivery(Delivery delivery) {
        delivery.setId(null);
        return deliveryRepository.save(delivery);
    }

    public Delivery updateDeliveryStatus(Long id, String deliveryStatus) {
        Delivery delivery = getDeliveryById(id);
        delivery.setDeliveryStatus(deliveryStatus);
        return deliveryRepository.save(delivery);
    }
}
