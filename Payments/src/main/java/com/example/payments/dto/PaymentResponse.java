package com.example.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payment response payload")
public class PaymentResponse {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "PAY-20250101-0001")
    private String reference;
    @Schema(example = "100.0")
    private Double amount;
    @Schema(example = "USD")
    private String currency;
    @Schema(example = "2025-01-01T12:00:00Z")
    private OffsetDateTime createdAt;
    @Schema(example = "a12b9c00-3a09-4d94-9f49-3f94bdaf12aa")
    private String clientRequestId;
}
