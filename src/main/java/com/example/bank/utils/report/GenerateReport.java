package com.example.bank.utils.report;

import com.example.bank.domain.transaction.Transaction;

import java.io.IOException;
import java.util.List;

public interface GenerateReport {
    public void generate(List<Transaction> reportData) throws IOException;
}
