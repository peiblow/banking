package com.example.bank.strategies.wallet;

import com.example.bank.domain.Wallet;
import com.example.bank.domain.transaction.CoinType;
import java.math.BigDecimal;

public interface CoinStrategy {
    Wallet pay(CoinType coinType, Wallet wallet, BigDecimal value) throws Exception;
}
