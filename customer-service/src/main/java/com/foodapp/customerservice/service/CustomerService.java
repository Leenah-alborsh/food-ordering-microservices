package com.foodapp.customerservice.service;

import com.foodapp.customerservice.exception.CustomerNotFoundException;
import com.foodapp.customerservice.model.Customer;
import com.foodapp.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
