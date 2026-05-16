package com.foodapp.kitchenservice.controller;

import com.foodapp.kitchenservice.model.KitchenOrder;
import com.foodapp.kitchenservice.service.KitchenService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kitchen/orders")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @GetMapping
    public List<KitchenOrder> getAllKitchenOrders() {
        return kitchenService.getAllKitchenOrders();
    }

    @GetMapping("/{id}")
    public KitchenOrder getKitchenOrderById(@PathVariable Long id) {
        return kitchenService.getKitchenOrderById(id);
    }

    @GetMapping("/order/{orderId}")
    public KitchenOrder getKitchenOrderByOrderId(@PathVariable Long orderId) {
        return kitchenService.getKitchenOrderByOrderId(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenOrder createKitchenOrder(@RequestBody KitchenOrder kitchenOrder) {
        return kitchenService.createKitchenOrder(kitchenOrder);
    }

    @PutMapping("/{id}/status")
    public KitchenOrder updateKitchenOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return kitchenService.updateKitchenOrderStatus(id, body.get("prepStatus"));
    }
}
