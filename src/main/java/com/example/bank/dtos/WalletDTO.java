package com.example.bank.dtos;

import java.math.BigDecimal;

public record WalletDTO(Long userId, BigDecimal brl, BigDecimal usd, BigDecimal btc) {
}
