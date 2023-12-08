package com.example.bank.repositories;

import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private  static final String document = "12345678900";

    @BeforeEach
    public void setup() {
        BigDecimal balance = new BigDecimal(2000);
        UserDTO userData = new UserDTO(
                "Pablo",
                "Fernandes",
                document,
                "pablo@gmail.com",
                balance,
                "123456789",
                UserType.COMMON
        );

        User user = new User(userData);
        userRepository.save(user);
    }

    @Test
    void itShouldFindUserByDocument() {
        Optional<User> user = userRepository.findUserByDocument(document);

        assertThat(user).isNotEmpty();
        assertThat(user.get().getDocument()).isEqualTo(document);
    }

    @Test
    void itShouldFindUserById() {
        Long id = 1L;
        Optional<User> user = userRepository.findUserById(id);

        assertThat(user).isNotEmpty();
        assertThat(user.get().getId()).isEqualTo(id);
    }
}