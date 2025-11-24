package com.example.payments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payment_sequences", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sequence_date"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence_date", nullable = false, unique = true)
    private LocalDate sequenceDate;

    @Column(name = "last_value", nullable = false)
    private Integer lastValue;
}
