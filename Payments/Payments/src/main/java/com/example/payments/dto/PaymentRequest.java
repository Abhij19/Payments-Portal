package com.example.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Payment Request Body")
public class PaymentRequest {

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be > 0")
    @Schema(example = "100.0")
    private Double amount;

    @NotNull(message = "currency is required")
    @Pattern(regexp = "USD|EUR|INR|GBP", message = "currency must be one of USD, EUR, INR, GBP")
    @Schema(example = "USD")
    private String currency;

    @NotNull(message = "clientRequestId is required")
    @Schema(example = "a12b9c00-3a09-4d94-9f49-3f94bdaf12aa")
    private String clientRequestId;
}
