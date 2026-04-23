package com.foodapp.ordering_service.repository;

import com.foodapp.ordering_service.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
