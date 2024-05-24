package com.example.bank.controllers;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.domain.transaction.TransactionType;
import com.example.bank.dtos.TransactionDTO;
import com.example.bank.factory.TransactionStrategyFactory;
import com.example.bank.services.TransactionService;
import com.example.bank.strategies.transactions.TransactionStrategy;
import com.example.bank.utils.report.GenerateCsvReport;
import com.example.bank.strategies.transactions.TransactionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transactions")
@Slf4j
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionStrategyFactory strategyFactory;

    @Autowired
    private GenerateCsvReport generateCsvReport;
    
    @Autowired
    private TransactionStrategyFactory strategyFactory;

    @GetMapping
    public ResponseEntity<List<Transaction>> getMyTransactions(@RequestParam(value = "id") Long id, @RequestParam(required = false, value = "type") TransactionType type) throws Exception {
        TransactionStrategy strategy = strategyFactory.getStrategy(type);
        List<Transaction> myTransactions = strategy.getTransactions(id);

        return new ResponseEntity<>(myTransactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createNewTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = transactionService.createTransaction(transaction);

        log.info("Transaction has made with success!");
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/export")
    public ResponseEntity exportTransaction (@RequestParam(value = "id") Long id ) throws Exception {
        transactionService.generateReport(id, generateCsvReport);

        return new ResponseEntity(HttpStatus.OK);
    }
}
