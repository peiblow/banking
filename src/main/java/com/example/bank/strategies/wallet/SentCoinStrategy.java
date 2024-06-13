package com.example.bank.strategies.wallet;

import com.example.bank.domain.Wallet;
import com.example.bank.domain.transaction.CoinType;
import com.example.bank.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.BiConsumer;

@Component
public class SentCoinStrategy implements CoinStrategy {
    private final UserService userService = new UserService();
    private Wallet wallet;
    private final HashMap<CoinType, BiConsumer<Wallet, BigDecimal>> strategies = new HashMap<>();

    public SentCoinStrategy() {
        strategies.put(CoinType.BRL, (wallet, value) -> {
            try {
                userService.validateTransaction(wallet.getWalletOwner(), value, wallet.getBrl());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            BigDecimal amount = wallet.getBrl().subtract(value);
            wallet.setBrl(amount);
        });
        strategies.put(CoinType.USD, (wallet, value) -> {
            try {
                userService.validateTransaction(wallet.getWalletOwner(), value, wallet.getUsd());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            BigDecimal amount = wallet.getUsd().subtract(value);
            wallet.setUsd(amount);
        });
        strategies.put(CoinType.BTC, (wallet, value) -> {
            try {
                userService.validateTransaction(wallet.getWalletOwner(), value, wallet.getBtc());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            BigDecimal amount = wallet.getBtc().subtract(value);
            wallet.setBtc(amount);
        });
    }

    @Override
    public Wallet pay(CoinType coinType, Wallet wallet, BigDecimal value) throws Exception {
        strategies.get(coinType).accept(wallet, value);
        return wallet;
    }
}
