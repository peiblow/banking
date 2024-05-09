package com.example.bank.domain.user;

import com.example.bank.dtos.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO userData) {
        this.firstname = userData.firstname();
        this.lastname = userData.lastname();
        this.document = userData.document();
        this.email = userData.email();
        this.password = userData.password();
        this.balance = userData.balance();
        this.userType = userData.type();
    }
}
