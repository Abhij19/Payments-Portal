package com.example.payments.service;

import com.example.payments.dto.PaymentRequest;
import com.example.payments.model.Payment;
import com.example.payments.model.PaymentSequence;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.repository.PaymentSequenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentSequenceRepository sequenceRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setup() {
    }

    @Test
    void createOrGet_whenNew_createsPayment() {
        PaymentRequest req = PaymentRequest.builder()
                .amount(100.0)
                .currency("USD")
                .clientRequestId("abc-123")
                .build();

        when(paymentRepository.findByClientRequestId("abc-123"))
                .thenReturn(Optional.empty());

        when(sequenceRepository.findBySequenceDateForUpdate(any(LocalDate.class)))
                .thenReturn(Optional.empty());

        when(sequenceRepository.save(any(PaymentSequence.class)))
                .thenAnswer(invocation -> {
                    PaymentSequence seq = invocation.getArgument(0);
                    if (seq.getLastValue() == null) seq.setLastValue(0);
                    seq.setId(1L);
                    return seq;
                });

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> {
                    Payment p = invocation.getArgument(0);
                    p.setId(1L);
                    p.setCreatedAt(OffsetDateTime.now());
                    return p;
                });

        Payment created = paymentService.createOrGet(req);

        assertNotNull(created);
        assertEquals(req.getAmount(), created.getAmount());
        assertEquals(req.getCurrency(), created.getCurrency());
        assertEquals(req.getClientRequestId(), created.getClientRequestId());
        assertTrue(created.getReference().startsWith("PAY-"));

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void createOrGet_whenDuplicate_returnsExisting() {
        PaymentRequest req = PaymentRequest.builder()
                .amount(100.0)
                .currency("USD")
                .clientRequestId("dup-1")
                .build();

        Payment existing = Payment.builder()
                .id(5L)
                .reference("PAY-20250101-0001")
                .amount(100.0)
                .currency("USD")
                .clientRequestId("dup-1")
                .createdAt(OffsetDateTime.now())
                .build();

        when(paymentRepository.findByClientRequestId("dup-1"))
                .thenReturn(Optional.of(existing));

        Payment result = paymentService.createOrGet(req);

        assertNotNull(result);
        assertEquals(existing.getId(), result.getId());

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }
}
