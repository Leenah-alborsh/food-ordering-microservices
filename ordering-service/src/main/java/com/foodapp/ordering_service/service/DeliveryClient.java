package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.DeliveryResponse;
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
public class DeliveryClient {

    private final RestTemplate restTemplate;

    @Value("${delivery-service.base-url}")
    private String deliveryServiceBaseUrl;

    public DeliveryResponse createDelivery(DeliveryResponse deliveryRequest) {
        return restTemplate.postForObject(
                deliveryServiceBaseUrl + "/api/deliveries",
                deliveryRequest,
                DeliveryResponse.class
        );
    }

    public DeliveryResponse findDeliveryByOrderId(Long orderId) {
        try {
            return restTemplate.getForObject(
                    deliveryServiceBaseUrl + "/api/deliveries/order/" + orderId,
                    DeliveryResponse.class
            );
        } catch (HttpClientErrorException.NotFound exception) {
            return null;
        }
    }

    public DeliveryResponse updateDeliveryStatus(Long id, String deliveryStatus) {
        ResponseEntity<DeliveryResponse> response = restTemplate.exchange(
                deliveryServiceBaseUrl + "/api/deliveries/" + id + "/status",
                HttpMethod.PUT,
                new HttpEntity<>(Map.of("deliveryStatus", deliveryStatus)),
                DeliveryResponse.class
        );
        return response.getBody();
    }
}
