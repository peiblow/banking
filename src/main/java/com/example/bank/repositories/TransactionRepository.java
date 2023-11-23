package com.example.bank.repositories;

import com.example.bank.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Optional<List<Transaction>> findTransactionByreceiver_idOrsent_id(Long id);
    Optional<List<Transaction>> findTransactionByreceiver_id(Long id);
    Optional<List<Transaction>> findTransactionBysent_id(Long id);
}
