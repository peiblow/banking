package com.example.bank.utils.report;

import com.example.bank.domain.transaction.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GenerateReport {
    public File generate(List<Transaction> reportData) throws IOException;
}
