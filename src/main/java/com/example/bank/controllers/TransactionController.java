package com.example.bank.controllers;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.domain.transaction.TransactionType;
import com.example.bank.dtos.TransactionDTO;
import com.example.bank.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getMyTransactions(@RequestParam(value = "id") Long id, @RequestParam(value = "type") TransactionType type) throws Exception {
        List<Transaction> myTransactions = switch (type) {
            case SENT -> this.transactionService.getUserSentTransactions(id);
            case RECEIVED -> this.transactionService.getUserReceivedTransactions(id);
            default -> this.transactionService.getUserTransactions(id);
        };

        return new ResponseEntity<>(myTransactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createNewTransaction(@RequestBody TransactionDTO transaction) throws Exception {
        Transaction newTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
}
