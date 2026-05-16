package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${payment-service.base-url}")
    private String paymentServiceBaseUrl;

    public PaymentResponse processPayment(PaymentResponse paymentRequest) {
        return restTemplate.postForObject(
                paymentServiceBaseUrl + "/api/payments/process",
                paymentRequest,
                PaymentResponse.class
        );
    }

    public PaymentResponse findPaymentByOrderId(Long orderId) {
        try {
            return restTemplate.getForObject(
                    paymentServiceBaseUrl + "/api/payments/order/" + orderId,
                    PaymentResponse.class
            );
        } catch (HttpClientErrorException.NotFound exception) {
            return null;
        }
    }
}
