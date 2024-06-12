package com.example.bank.domain;

import com.example.bank.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

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

    private Double brl;
    private Double usd;
    private Double btc;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Wallet(User owner, Double brl, Double usd, Double btc) {
        this.walletOwner = owner;
        this.brl = brl;
        this.usd = usd;
        this.btc = btc;
    }
}
