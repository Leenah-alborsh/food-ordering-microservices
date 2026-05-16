package com.foodapp.customerservice.grpc;

import com.foodapp.customerservice.model.Customer;
import com.foodapp.customerservice.service.CustomerService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class CustomerGrpcEndpoint extends CustomerGrpcServiceGrpc.CustomerGrpcServiceImplBase {

    private final CustomerService customerService;

    @Override
    public void getCustomerById(CustomerByIdRequest request, StreamObserver<CustomerReply> responseObserver) {
        Customer customer = customerService.getCustomerById(request.getId());

        CustomerReply reply = CustomerReply.newBuilder()
                .setId(customer.getId())
                .setName(customer.getName())
                .setEmail(customer.getEmail())
                .setAddress(customer.getAddress())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
