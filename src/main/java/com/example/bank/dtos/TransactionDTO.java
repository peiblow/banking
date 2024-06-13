package com.example.bank.dtos;

import com.example.bank.domain.transaction.CoinType;

import java.io.Serializable;
import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long sentId, Long receiverId, CoinType coinType) implements Serializable {
}
