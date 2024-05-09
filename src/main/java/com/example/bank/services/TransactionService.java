package com.example.bank.services;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.domain.user.User;
import com.example.bank.dtos.TransactionDTO;
import com.example.bank.repositories.TransactionRepository;
import com.example.bank.utils.report.GenerateReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    @Cacheable("userTransaction")
    public  List<Transaction> getUserTransactions(Long id) throws Exception {
        return this.repository.findTransactionByreceiver_id(id).orElseThrow(() -> new Exception("Nenhuma transação encontrada"));
    }

    @Cacheable("userTransaction")
    public List<Transaction> getUserReceivedTransactions(Long id) throws Exception {
        return this.repository.findTransactionByreceiver_id(id).orElseThrow(() -> new Exception("Este usuário não recebeu nenhuma transação"));
    }

    @Cacheable("userTransaction")
    public List<Transaction> getUserSentTransactions(Long id) throws Exception {
        return this.repository.findTransactionBysent_id(id).orElseThrow(() -> new Exception("Este usuário não efetuou nenhuma transação"));
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sent = this.userService.getUserById(transactionDTO.sentId());
        User receiver = this.userService.getUserById(transactionDTO.receiverId());

        userService.validateTransaction(sent, transactionDTO.value());

        boolean isAuthorized = authorizedTransaction(sent, transactionDTO.value());
        if (!isAuthorized) {
            log.error("Transação não autorizada");
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setSent(sent);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sent.setBalance(sent.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sent);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sent, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Você recebeu uma nova transação!");

        return newTransaction;
    }

    public Boolean authorizedTransaction (User sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
        String message = authorizationResponse.getBody().get("message").toString();

        return authorizationResponse.getStatusCode() == HttpStatus.OK && message.equals("Autorizado");
    }

    public void generateReport (Long userId, GenerateReport report) throws Exception {
        log.info("INICIANDO GERAÇÃO DE REPORT");

        try {
            List<Transaction> reportData = this.getUserTransactions(userId);
            report.generate(reportData);
        } catch (Exception e) {
            log.error("NÃO FOI POSSIVEL GERAR O REPORT");
            throw new Exception("Não foi possivel gerar o report: " + e.getMessage());
        }
    }
}
