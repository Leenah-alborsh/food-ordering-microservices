package com.foodapp.customerservice.controller;

import com.foodapp.customerservice.model.Customer;
import com.foodapp.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CustomerGraphQlController {

    private final CustomerService customerService;

    @QueryMapping
    public Customer customerById(@Argument Long id) {
        return customerService.getCustomerById(id);
    }
}
