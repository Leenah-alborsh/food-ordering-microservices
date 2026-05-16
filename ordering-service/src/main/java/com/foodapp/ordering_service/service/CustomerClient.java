package com.foodapp.ordering_service.service;

import com.foodapp.ordering_service.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class CustomerClient {

    private final RestTemplate restTemplate;

    @Value("${customer-service.base-url}")
    private String customerServiceBaseUrl;

    public CustomerResponse getCustomerById(Long customerId) {
        return restTemplate.getForObject(
                customerServiceBaseUrl + "/api/customers/" + customerId,
                CustomerResponse.class
        );
    }
}
