package com.example.payments.service;

import com.example.payments.dto.PaymentRequest;
import com.example.payments.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment createOrGet(PaymentRequest request);
    List<Payment> listAll();
    Optional<Payment> findById(Long id);
    Payment update(Long id, PaymentRequest request);
    void delete(Long id);
}
