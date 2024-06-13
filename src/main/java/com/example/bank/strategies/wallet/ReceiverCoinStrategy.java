package com.example.bank.strategies.wallet;

import com.example.bank.domain.Wallet;
import com.example.bank.domain.transaction.CoinType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.BiConsumer;

@Component
public class ReceiverCoinStrategy implements CoinStrategy{
    private Wallet wallet;
    private final HashMap<CoinType, BiConsumer<Wallet, BigDecimal>> strategies = new HashMap<>();

    public ReceiverCoinStrategy() {
        strategies.put(CoinType.BRL, (wallet, value) -> {
            BigDecimal amount = wallet.getBrl().add(value);
            wallet.setBrl(amount);
        });
        strategies.put(CoinType.USD, (wallet, value) -> {
            BigDecimal amount = wallet.getUsd().add(value);
            wallet.setUsd(amount);
        });
        strategies.put(CoinType.BTC, (wallet, value) -> {
            BigDecimal amount = wallet.getBtc().add(value);
            wallet.setBtc(amount);
        });
    }

    @Override
    public Wallet pay(CoinType coinType, Wallet wallet, BigDecimal value) throws Exception {
        strategies.get(coinType).accept(wallet, value);
        return wallet;
    }
}
