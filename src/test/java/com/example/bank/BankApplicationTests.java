package com.example.bank;

import com.example.bank.integrationtests.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankApplicationTests extends AbstractIntegrationTest {

	@Test
	void contextLoads() {
	}
}
