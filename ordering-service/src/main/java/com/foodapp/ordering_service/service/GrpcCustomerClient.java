package com.foodapp.ordering_service.service;

import com.foodapp.customerservice.grpc.CustomerByIdRequest;
import com.foodapp.customerservice.grpc.CustomerGrpcServiceGrpc;
import com.foodapp.customerservice.grpc.CustomerReply;
import com.foodapp.ordering_service.dto.CustomerResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class GrpcCustomerClient {

    @GrpcClient("customer-service")
    private CustomerGrpcServiceGrpc.CustomerGrpcServiceBlockingStub customerStub;

    public CustomerResponse getCustomerById(Long customerId) {
        CustomerReply reply = customerStub.getCustomerById(
                CustomerByIdRequest.newBuilder()
                        .setId(customerId)
                        .build()
        );

        CustomerResponse customer = new CustomerResponse();
        customer.setId(reply.getId());
        customer.setName(reply.getName());
        customer.setEmail(reply.getEmail());
        customer.setAddress(reply.getAddress());
        return customer;
    }
}
