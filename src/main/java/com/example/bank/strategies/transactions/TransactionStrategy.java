package com.example.bank.strategies.transactions;

import com.example.bank.domain.transaction.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionStrategy {
    Page<Transaction> getTransactions(Long id) throws Exception;
}
