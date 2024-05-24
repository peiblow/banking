package com.example.bank.factory;

import com.example.bank.domain.transaction.TransactionType;
import com.example.bank.strategies.transactions.AllTransactionStrategy;
import com.example.bank.strategies.transactions.ReceivedTransactionStrategy;
import com.example.bank.strategies.transactions.SentTransactionStrategy;
import com.example.bank.strategies.transactions.TransactionStrategy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class TransactionStrategyFactory {
    private final HashMap<TransactionType, TransactionStrategy> strategies = new HashMap<>();

    public TransactionStrategyFactory(
            SentTransactionStrategy sentStrategy,
            ReceivedTransactionStrategy receivedStrategy,
            AllTransactionStrategy allStrategy
    ) {
        strategies.put(TransactionType.SENT, sentStrategy);
        strategies.put(TransactionType.RECEIVED, receivedStrategy);
        strategies.put(null, allStrategy);
    }

    public TransactionStrategy getStrategy(TransactionType type) {
        return Optional.ofNullable(strategies.get(type)).orElseThrow(() -> new IllegalArgumentException("Invalid transaction type"));
    }
}
