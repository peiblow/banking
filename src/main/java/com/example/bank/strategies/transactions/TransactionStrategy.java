package com.example.bank.strategies.transactions;

import com.example.bank.domain.transaction.Transaction;

import java.util.List;

public interface TransactionStrategy {
    List<Transaction> getTransactions(Long id) throws Exception;
}
