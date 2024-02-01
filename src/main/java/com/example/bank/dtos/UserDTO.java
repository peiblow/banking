package com.example.bank.dtos;

import com.example.bank.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstname, String lastname, String document, String email, BigDecimal balance, String password, UserType type) {
}
