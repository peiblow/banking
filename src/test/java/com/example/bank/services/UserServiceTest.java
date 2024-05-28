package com.example.bank.services;

import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.UserDTO;
import com.example.bank.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private User user;
    private UserDTO userData;
    private  static final String DOCUMENT = "12345678900";

    @BeforeEach
    public void setup() {
        BigDecimal balance = new BigDecimal(2000);
        userData = new UserDTO(
                "Pablo",
                "Fernandez",
                DOCUMENT,
                "pablo@gmail.com",
                balance,
                "123456789",
                UserType.COMMON
        );

        user = new User(userData);
    }

    @Test
    void itShouldCreateUser() {
        when(repository.save(user)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("#46@#SASD!6");

        User savedUser = service.createUser(userData);
        verify(repository).save(user);
        verify(passwordEncoder).encode(user.getPassword());

        assertNotNull(savedUser);
        assertEquals(DOCUMENT, savedUser.getDocument());
        assertEquals("#46@#SASD!6", savedUser.getPassword());
    }

    @Test
    void itShouldReturnSpecifiedUserById() throws Exception {
        when(repository.findUserById(1L)).thenReturn(Optional.of(user));

        User findedUser = service.getUserById(1L);

        assertNotNull(findedUser);
        assertEquals(user.getDocument(), findedUser.getDocument());
    }

    @Test
    void itShouldThrowAnExceptionWhenNotFindUser() throws Exception {
        when(repository.findUserById(1L)).thenReturn(null);

        verify(repository, never()).findUserById(1L);

        assertThrows(Exception.class, () -> {
            service.getUserById(1L);
        });
    }
}
