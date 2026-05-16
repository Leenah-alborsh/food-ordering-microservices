package com.foodapp.paymentservice.service;

import com.foodapp.paymentservice.exception.PaymentNotFoundException;
import com.foodapp.paymentservice.model.Payment;
import com.foodapp.paymentservice.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
    }

    public Payment processPayment(Payment payment) {
        payment.setId(null);
        return paymentRepository.save(payment);
    }
}
