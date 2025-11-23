package com.example.payments.service;

import com.example.payments.dto.PaymentRequest;
import com.example.payments.exception.PaymentNotFoundException;
import com.example.payments.model.Payment;
import com.example.payments.model.PaymentSequence;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.repository.PaymentSequenceRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentSequenceRepository sequenceRepository;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String CB_NAME = "paymentService";

    @Override
    @Transactional
    @CircuitBreaker(name = CB_NAME, fallbackMethod = "createFallback")
    @CacheEvict(value = "payments", allEntries = true)
    public Payment createOrGet(PaymentRequest request) {
        log.info("Checking for duplicate clientRequestId={}", request.getClientRequestId());
        Optional<Payment> existing = paymentRepository.findByClientRequestId(request.getClientRequestId());
        if (existing.isPresent()) return existing.get();

        LocalDate today = LocalDate.now();
        log.info("Generating sequence for {}", today);
        PaymentSequence seq = sequenceRepository.findBySequenceDateForUpdate(today)
                .orElseGet(() -> sequenceRepository.save(PaymentSequence.builder().sequenceDate(today).lastValue(0).build()));

        int next = seq.getLastValue() + 1;
        seq.setLastValue(next);
        sequenceRepository.save(seq);

        String ref = String.format("PAY-%s-%04d", DATE_FMT.format(today), next);
        log.info("Generated reference {}", ref);

        Payment p = Payment.builder()
                .reference(ref)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .createdAt(OffsetDateTime.now())
                .clientRequestId(request.getClientRequestId())
                .build();

        Payment saved =  paymentRepository.save(p);
        log.info("Payment saved: {}", saved.getReference());
        return saved;
    }

    public Payment createFallback(PaymentRequest request, Throwable t) {
        log.error("createOrGet fallback triggered due to {}", t.getMessage());
        throw new RuntimeException("Service temporarily unavailable. Please retry later.");
    }

    @Override
    @Cacheable("payments")
    public List<Payment> listAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "payments", allEntries = true)
    public Payment update(Long id, PaymentRequest request) {
        log.info("Updating payment {} with {}", id, request);
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + id));
        p.setAmount(request.getAmount());
        p.setCurrency(request.getCurrency());
        return paymentRepository.save(p);
    }

    @Override
    @Transactional
    @CacheEvict(value = "payments", allEntries = true)
    public void delete(Long id) {
        log.warn("Deleting payment {}", id);
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
