package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.CustomerResponse;
import com.foodapp.ordering_service.dto.OrderRequest;
import com.foodapp.ordering_service.dto.OrderResponse;
import com.foodapp.ordering_service.model.OrderEntity;
import com.foodapp.ordering_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final GraphQlCustomerClient graphQlCustomerClient;
    private final GrpcCustomerClient grpcCustomerClient;

    public OrderResponse createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.getCustomerById(request.getCustomerId());
        return buildOrderResponse(request, customer, "REST");
    }

    public OrderResponse createOrderUsingGraphQl(OrderRequest request) {
        CustomerResponse customer = graphQlCustomerClient.getCustomerById(request.getCustomerId());
        return buildOrderResponse(request, customer, "GraphQL");
    }

    public OrderResponse createOrderUsingGrpc(OrderRequest request) {
        CustomerResponse customer = grpcCustomerClient.getCustomerById(request.getCustomerId());
        return buildOrderResponse(request, customer, "gRPC");
    }

    private OrderResponse buildOrderResponse(OrderRequest request, CustomerResponse customer, String communicationType) {
        OrderEntity order = new OrderEntity();
        order.setCustomerId(request.getCustomerId());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());

        OrderEntity savedOrder = orderRepository.save(order);
        return new OrderResponse(savedOrder, customer, communicationType);
    }
}
