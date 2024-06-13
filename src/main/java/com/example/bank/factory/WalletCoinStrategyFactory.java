package com.example.bank.factory;

import com.example.bank.domain.transaction.TransactionType;
import com.example.bank.strategies.wallet.CoinStrategy;
import com.example.bank.strategies.wallet.ReceiverCoinStrategy;
import com.example.bank.strategies.wallet.SentCoinStrategy;
import org.springframework.stereotype.Component;
import java.util.HashMap;

@Component
public class WalletCoinStrategyFactory {
    private final HashMap<TransactionType, CoinStrategy> strategies = new HashMap<>();

    public WalletCoinStrategyFactory(
            SentCoinStrategy sentCoinStrategy,
            ReceiverCoinStrategy receiverCoinStrategy
    ) {
        strategies.put(TransactionType.SENT, sentCoinStrategy);
        strategies.put(TransactionType.RECEIVED, receiverCoinStrategy);
    }

    public CoinStrategy getStrategy(TransactionType type) {
        return strategies.get(type);
    }
}
