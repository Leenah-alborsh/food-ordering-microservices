package com.foodapp.kitchenservice.service;

import com.foodapp.kitchenservice.exception.KitchenOrderNotFoundException;
import com.foodapp.kitchenservice.model.KitchenOrder;
import com.foodapp.kitchenservice.repository.KitchenOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenService {

    private final KitchenOrderRepository kitchenOrderRepository;

    public List<KitchenOrder> getAllKitchenOrders() {
        return kitchenOrderRepository.findAll();
    }

    public KitchenOrder getKitchenOrderById(Long id) {
        return kitchenOrderRepository.findById(id)
                .orElseThrow(() -> new KitchenOrderNotFoundException(id));
    }

    public KitchenOrder getKitchenOrderByOrderId(Long orderId) {
        return kitchenOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new KitchenOrderNotFoundException(orderId));
    }

    public KitchenOrder createKitchenOrder(KitchenOrder kitchenOrder) {
        kitchenOrder.setId(null);
        return kitchenOrderRepository.save(kitchenOrder);
    }

    public KitchenOrder updateKitchenOrderStatus(Long id, String prepStatus) {
        KitchenOrder kitchenOrder = getKitchenOrderById(id);
        kitchenOrder.setPrepStatus(prepStatus);
        return kitchenOrderRepository.save(kitchenOrder);
    }
}
