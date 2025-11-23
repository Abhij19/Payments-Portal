package com.example.payments.repository;

import com.example.payments.model.PaymentSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface PaymentSequenceRepository extends JpaRepository<PaymentSequence, Long> {

    Optional<PaymentSequence> findBySequenceDate(LocalDate date);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PaymentSequence p where p.sequenceDate = :date")
    Optional<PaymentSequence> findBySequenceDateForUpdate(@Param("date") LocalDate date);
}
