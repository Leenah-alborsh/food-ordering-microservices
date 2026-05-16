package com.foodapp.ordering_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodapp.ordering_service.dto.CustomerResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GraphQlCustomerClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${customer-service.graphql-url}")
    private String customerServiceGraphQlUrl;

    public CustomerResponse getCustomerById(Long customerId) {
        String query = "query($id: ID!) { customerById(id: $id) { id name email address } }";
        Map<String, Object> requestBody = Map.of(
                "query", query,
                "variables", Map.of("id", customerId.toString())
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JsonNode response = restTemplate.postForObject(
                customerServiceGraphQlUrl,
                new HttpEntity<>(requestBody, headers),
                JsonNode.class
        );

        JsonNode customerNode = response.path("data").path("customerById");
        return objectMapper.convertValue(customerNode, CustomerResponse.class);
    }
}
