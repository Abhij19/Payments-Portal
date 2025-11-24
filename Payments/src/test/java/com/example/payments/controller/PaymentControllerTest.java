package com.example.payments.controller;

import com.example.payments.dto.PaymentRequest;
import com.example.payments.model.Payment;
import com.example.payments.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    void list_returnsPayments() throws Exception {
        Payment p = Payment.builder()
                .id(1L)
                .reference("PAY-20250101-0001")
                .amount(100.0)
                .currency("USD")
                .createdAt(OffsetDateTime.now())
                .clientRequestId("abc")
                .build();

        when(paymentService.listAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].reference").value("PAY-20250101-0001"));
    }

    @Test
    void create_callsService() throws Exception {
        PaymentRequest req = PaymentRequest.builder()
                .amount(50.0)
                .currency("USD")
                .clientRequestId("x123")
                .build();

        Payment saved = Payment.builder()
                .id(2L)
                .reference("PAY-20250101-0002")
                .amount(50.0)
                .currency("USD")
                .createdAt(OffsetDateTime.now())
                .clientRequestId("x123")
                .build();

        when(paymentService.createOrGet(any())).thenReturn(saved);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reference").value("PAY-20250101-0002"));
    }
}
