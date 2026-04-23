package com.foodapp.ordering_service.dto;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
}
