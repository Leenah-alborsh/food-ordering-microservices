package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.CustomerResponse;
import com.foodapp.ordering_service.dto.DeliveryResponse;
import com.foodapp.ordering_service.dto.KitchenOrderResponse;
import com.foodapp.ordering_service.dto.MenuItemResponse;
import com.foodapp.ordering_service.dto.OrderRequest;
import com.foodapp.ordering_service.dto.OrderResponse;
import com.foodapp.ordering_service.dto.PaymentResponse;
import com.foodapp.ordering_service.exception.OrderNotFoundException;
import com.foodapp.ordering_service.model.OrderEntity;
import com.foodapp.ordering_service.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final GraphQlCustomerClient graphQlCustomerClient;
    private final GrpcCustomerClient grpcCustomerClient;
    private final MenuClient menuClient;
    private final PaymentClient paymentClient;
    private final KitchenClient kitchenClient;
    private final DeliveryClient deliveryClient;

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toOrderResponse(order);
    }

    public OrderResponse createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.getCustomerById(request.getCustomerId());
        return createOrderWorkflow(request, customer, "REST");
    }

    public OrderResponse createOrderUsingGraphQl(OrderRequest request) {
        CustomerResponse customer = graphQlCustomerClient.getCustomerById(request.getCustomerId());
        return createOrderWorkflow(request, customer, "GraphQL");
    }

    public OrderResponse createOrderUsingGrpc(OrderRequest request) {
        CustomerResponse customer = grpcCustomerClient.getCustomerById(request.getCustomerId());
        return createOrderWorkflow(request, customer, "gRPC");
    }

    private OrderResponse createOrderWorkflow(OrderRequest request, CustomerResponse customer, String communicationType) {
        MenuItemResponse menuItem = menuClient.getMenuItemById(request.getMenuItemId());

        OrderEntity order = new OrderEntity();
        order.setCustomerId(request.getCustomerId());
        order.setMenuItemId(request.getMenuItemId());
        order.setItemName(menuItem.getName());
        order.setUnitPrice(menuItem.getPrice());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(menuItem.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        order.setStatus("CREATED");

        OrderEntity savedOrder = orderRepository.save(order);

        PaymentResponse paymentRequest = new PaymentResponse();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setCustomerId(savedOrder.getCustomerId());
        paymentRequest.setAmount(savedOrder.getTotalPrice());
        paymentRequest.setPaymentMethod(resolvePaymentMethod(request.getPaymentMethod()));
        paymentRequest.setPaymentStatus(resolvePaymentStatus(request.getPaymentMethod()));
        paymentRequest.setTransactionDate(LocalDateTime.now());

        PaymentResponse payment = paymentClient.processPayment(paymentRequest);
        savedOrder.setStatus("SUCCESS".equalsIgnoreCase(payment.getPaymentStatus()) ? "PAID" : "PAYMENT_FAILED");
        savedOrder = orderRepository.save(savedOrder);

        KitchenOrderResponse kitchenOrder = null;
        if ("SUCCESS".equalsIgnoreCase(payment.getPaymentStatus())) {
            KitchenOrderResponse kitchenRequest = new KitchenOrderResponse();
            kitchenRequest.setOrderId(savedOrder.getId());
            kitchenRequest.setItemName(savedOrder.getItemName());
            kitchenRequest.setQuantity(savedOrder.getQuantity());
            kitchenRequest.setPrepStatus("PENDING_PREPARATION");
            kitchenRequest.setNotes("Created by ordering-service after successful payment");
            kitchenOrder = kitchenClient.createKitchenOrder(kitchenRequest);
            savedOrder.setStatus("PREPARING");
            savedOrder = orderRepository.save(savedOrder);
        }

        return new OrderResponse(savedOrder, customer, menuItem, payment, kitchenOrder, null, communicationType);
    }

    private OrderResponse toOrderResponse(OrderEntity order) {
        CustomerResponse customer = customerClient.getCustomerById(order.getCustomerId());
        MenuItemResponse menuItem = order.getMenuItemId() != null
                ? menuClient.getMenuItemById(order.getMenuItemId())
                : null;
        PaymentResponse payment = paymentClient.findPaymentByOrderId(order.getId());
        KitchenOrderResponse kitchenOrder = kitchenClient.findKitchenOrderByOrderId(order.getId());
        DeliveryResponse delivery = deliveryClient.findDeliveryByOrderId(order.getId());

        if (kitchenOrder != null && "READY".equalsIgnoreCase(kitchenOrder.getPrepStatus()) && delivery == null) {
            DeliveryResponse deliveryRequest = new DeliveryResponse();
            deliveryRequest.setOrderId(order.getId());
            deliveryRequest.setCustomerId(order.getCustomerId());
            deliveryRequest.setDeliveryAddress(customer.getAddress());
            deliveryRequest.setCourierName("Courier One");
            deliveryRequest.setDeliveryStatus("ASSIGNED");
            delivery = deliveryClient.createDelivery(deliveryRequest);
        }

        synchronizeOrderStatus(order, payment, kitchenOrder, delivery);
        return new OrderResponse(order, customer, menuItem, payment, kitchenOrder, delivery, "REST");
    }

    private void synchronizeOrderStatus(OrderEntity order, PaymentResponse payment, KitchenOrderResponse kitchenOrder, DeliveryResponse delivery) {
        String nextStatus = order.getStatus() != null ? order.getStatus() : "CREATED";

        if (delivery != null) {
            nextStatus = mapDeliveryStatus(delivery.getDeliveryStatus());
        } else if (kitchenOrder != null) {
            nextStatus = mapKitchenStatus(kitchenOrder.getPrepStatus());
        } else if (payment != null && "SUCCESS".equalsIgnoreCase(payment.getPaymentStatus())) {
            nextStatus = "PAID";
        }

        if (!nextStatus.equals(order.getStatus())) {
            order.setStatus(nextStatus);
            orderRepository.save(order);
        }
    }

    private String mapKitchenStatus(String prepStatus) {
        if ("READY".equalsIgnoreCase(prepStatus)) {
            return "READY";
        }
        return "PREPARING";
    }

    private String mapDeliveryStatus(String deliveryStatus) {
        if ("DELIVERED".equalsIgnoreCase(deliveryStatus)) {
            return "DELIVERED";
        }
        if ("OUT_FOR_DELIVERY".equalsIgnoreCase(deliveryStatus)) {
            return "OUT_FOR_DELIVERY";
        }
        return "READY";
    }

    private String resolvePaymentMethod(String paymentMethod) {
        return paymentMethod == null || paymentMethod.isBlank() ? "CARD" : paymentMethod;
    }

    private String resolvePaymentStatus(String paymentMethod) {
        return "FAIL".equalsIgnoreCase(paymentMethod) ? "FAILED" : "SUCCESS";
    }
}
