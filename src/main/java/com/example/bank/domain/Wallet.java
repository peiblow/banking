package com.example.bank.domain;

import com.example.bank.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "wallets")
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "wallet_owner")
    private User walletOwner;

    private BigDecimal brl;
    private BigDecimal usd;
    private BigDecimal btc;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Wallet(User owner, BigDecimal brl, BigDecimal usd, BigDecimal btc) {
        this.walletOwner = owner;
        this.brl = brl;
        this.usd = usd;
        this.btc = btc;
    }
}
