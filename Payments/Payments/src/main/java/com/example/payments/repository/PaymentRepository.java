package com.example.payments.repository;

import com.example.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByClientRequestId(String clientRequestId);
}
