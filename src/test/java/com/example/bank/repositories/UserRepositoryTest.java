package com.example.bank.repositories;

import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private  static final String DOCUMENT = "12345678900";

    @BeforeEach
    public void setup() {
        BigDecimal balance = new BigDecimal(2000);
        UserDTO userData = new UserDTO(
                "Pablo",
                "Fernandez",
                DOCUMENT,
                "pablo22@gmail.com",
                balance,
                "123456789",
                UserType.COMMON
        );

        User user = new User(userData);
        userRepository.save(user);
    }

    @Test
    void itShouldFindUserByDocument() {
        Optional<User> user = userRepository.findUserByDocument(DOCUMENT);

        String userDocument =  user.get().getDocument();

        assertNotNull(user);
        assertEquals(userDocument, DOCUMENT);
    }

    @Test
    void itShouldFindUserById() {
        Long id = 1L;
        Optional<User> user = userRepository.findUserById(id);

        Long userId = user.get().getId();

        assertNotNull(user);
        assertEquals(userId, id);
    }
}