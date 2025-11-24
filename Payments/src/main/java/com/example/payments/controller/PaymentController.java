package com.example.payments.controller;

import com.example.payments.dto.PaymentRequest;
import com.example.payments.dto.PaymentResponse;
import com.example.payments.model.Payment;
import com.example.payments.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody PaymentRequest request) {
        log.info("Received create request: {}", request);
        Payment p = paymentService.createOrGet(request);
        log.info("Payment created: {}", p.getReference());
        return ResponseEntity.ok(toDto(p));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> list() {
        log.info("Fetching all payments");
        List<PaymentResponse> list = paymentService.listAll().stream().map(this::toDto).collect(Collectors.toList());
        log.info("Fetched {} payments", list.size());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> get(@PathVariable Long id) {
        log.info("Fetching payment by ID: {}", id);
        Payment p = paymentService.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return ResponseEntity.ok(toDto(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> update(@PathVariable Long id, @Valid @RequestBody PaymentRequest request) {
        log.info("Updating payment {}, body={}", id, request);
        Payment p = paymentService.update(id, request);
        return ResponseEntity.ok(toDto(p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("Deleting payment {}", id);
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private PaymentResponse toDto(Payment p) {
        return PaymentResponse.builder()
                .id(p.getId())
                .reference(p.getReference())
                .amount(p.getAmount())
                .currency(p.getCurrency())
                .createdAt(p.getCreatedAt())
                .clientRequestId(p.getClientRequestId())
                .build();
    }
}
