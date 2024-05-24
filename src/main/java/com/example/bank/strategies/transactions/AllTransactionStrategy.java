package com.example.bank.strategies.transactions;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.services.TransactionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllTransactionStrategy implements TransactionStrategy {
    private final TransactionService transactionService;

    public AllTransactionStrategy(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public List<Transaction> getTransactions(Long id) throws Exception {
        return transactionService.getUserTransactions(id);
    }
}
