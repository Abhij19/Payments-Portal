package com.example.payments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "payments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_request_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="reference", nullable=false, unique=true)
    private String reference;

    @Column(name="amount", nullable=false)
    private Double amount;

    @Column(name="currency", nullable=false, length=3)
    private String currency;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @Column(name="client_request_id", nullable=false, unique=true)
    private String clientRequestId;

}
