package com.example.bank.repositories;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.domain.user.User;
import com.example.bank.domain.user.UserType;
import com.example.bank.dtos.TransactionDTO;
import com.example.bank.dtos.UserDTO;
import com.example.bank.integrationtests.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private Pageable pageable;

    private  static final String DOCUMENT = "12345678900";

    @BeforeAll
    public void setup() {
        pageable = PageRequest.of(0, 5, Sort.by("timestamp"));
        BigDecimal balance = new BigDecimal(2000);

        User sent = createUser("Pablo", "Fernandez", DOCUMENT, "pablo@gmail.com", balance);
        User receiver = createUser("Peiblow", "Santos", "15985286289", "peiblow@gmail.com", balance);

        TransactionDTO transactionData = new TransactionDTO(balance, 1L, 2L);
        Transaction transaction = new Transaction(transactionData, sent, receiver);

        transactionRepository.save(transaction);
    }

    private User createUser(String firstName, String lastName, String document, String email, BigDecimal balance) {
        UserDTO userDTO = new UserDTO(firstName, lastName, document, email, balance, "test@123456", UserType.COMMON);
        User user = new User(userDTO);
        return userRepository.save(user);
    }

    @Test
    void itShouldFindTransactionByReceiverId() {
        List<Transaction> transactions = transactionRepository.findTransactionByReceiverId(2L, pageable).getContent();

        assertFalse(transactions.isEmpty(), () -> "Transactions should be present and not empty");

        Transaction transaction = transactions.get(0);
        BigDecimal amountExpected = new BigDecimal("2000.00");


        assertNotNull(transaction);
        assertEquals(amountExpected, transaction.getAmount());
        assertEquals(2L, transaction.getReceiver().getId());
    }

    @Test
    void itShouldFindTransactionBySentId() {
        List<Transaction> transactions = transactionRepository.findByReceiverIdOrSentId(1L, pageable).getContent();

        assertFalse(transactions.isEmpty(), () -> "Transactions should be present and not empty");

        Transaction transaction = transactions.get(0);
        BigDecimal amountExpected = new BigDecimal("2000.00");


        assertNotNull(transaction);
        assertEquals(amountExpected, transaction.getAmount());
        assertEquals(1L, transaction.getSent().getId());
    }
}
