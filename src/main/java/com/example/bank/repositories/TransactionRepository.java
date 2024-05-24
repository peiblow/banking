package com.example.bank.repositories;

import com.example.bank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM transactions t WHERE t.receiver.id = :id OR t.sent.id = :id")
    Optional<List<Transaction>> findByReceiverIdOrSentId(@Param("id") Long id);
    Optional<List<Transaction>> findTransactionByReceiverId(Long id);
    Optional<List<Transaction>> findTransactionBySentId(Long id);
}
