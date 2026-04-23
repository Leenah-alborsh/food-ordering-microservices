package com.foodapp.ordering_service.controller;

import com.foodapp.ordering_service.dto.OrderRequest;
import com.foodapp.ordering_service.dto.OrderResponse;
import com.foodapp.ordering_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/graphql")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderUsingGraphQl(@RequestBody OrderRequest request) {
        return orderService.createOrderUsingGraphQl(request);
    }

    @PostMapping("/grpc")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderUsingGrpc(@RequestBody OrderRequest request) {
        return orderService.createOrderUsingGrpc(request);
    }
}
