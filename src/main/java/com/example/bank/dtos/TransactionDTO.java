package com.example.bank.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long sentId, Long receiverId) implements Serializable {
}
