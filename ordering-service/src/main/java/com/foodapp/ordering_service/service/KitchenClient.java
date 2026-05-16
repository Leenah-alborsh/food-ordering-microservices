package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.KitchenOrderResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KitchenClient {

    private final RestTemplate restTemplate;

    @Value("${kitchen-service.base-url}")
    private String kitchenServiceBaseUrl;

    public KitchenOrderResponse createKitchenOrder(KitchenOrderResponse kitchenOrderRequest) {
        return restTemplate.postForObject(
                kitchenServiceBaseUrl + "/api/kitchen/orders",
                kitchenOrderRequest,
                KitchenOrderResponse.class
        );
    }

    public KitchenOrderResponse findKitchenOrderByOrderId(Long orderId) {
        try {
            return restTemplate.getForObject(
                    kitchenServiceBaseUrl + "/api/kitchen/orders/order/" + orderId,
                    KitchenOrderResponse.class
            );
        } catch (HttpClientErrorException.NotFound exception) {
            return null;
        }
    }

    public KitchenOrderResponse updateKitchenOrderStatus(Long id, String prepStatus) {
        ResponseEntity<KitchenOrderResponse> response = restTemplate.exchange(
                kitchenServiceBaseUrl + "/api/kitchen/orders/" + id + "/status",
                HttpMethod.PUT,
                new HttpEntity<>(Map.of("prepStatus", prepStatus)),
                KitchenOrderResponse.class
        );
        return response.getBody();
    }
}
