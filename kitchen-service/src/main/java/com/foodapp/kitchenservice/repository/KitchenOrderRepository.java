package com.foodapp.kitchenservice.repository;

import com.foodapp.kitchenservice.model.KitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface KitchenOrderRepository extends JpaRepository<KitchenOrder, Long> {
    Optional<KitchenOrder> findByOrderId(Long orderId);
}
