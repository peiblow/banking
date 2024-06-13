package com.example.bank.domain.transaction;

import com.example.bank.domain.user.User;
import com.example.bank.dtos.TransactionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private CoinType coinType;

    @ManyToOne
    @JoinColumn(name = "sent_id")
    private User sent;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private LocalDateTime timestamp;

    public Transaction(TransactionDTO transactionDTO, User sent, User receiver) {
        this.amount = transactionDTO.value();
        this.coinType = transactionDTO.coinType();
        this.sent = sent;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }
}
