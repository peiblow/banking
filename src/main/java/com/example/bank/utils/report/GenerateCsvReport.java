package com.example.bank.utils.report;

import com.example.bank.domain.transaction.Transaction;
import com.example.bank.utils.aws.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GenerateCsvReport implements GenerateReport {
    @Override
    public File generate(List<Transaction> reportData) throws IOException {
        List<String> dataLines = convertToCsv(reportData);

        String userDirectory = new File("").getAbsolutePath();
        File csvOutputFile = new File(userDirectory + "tmp/report.csv");

        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("Receiver, Sent, Amount");
            dataLines.stream().forEach(pw::println);

            log.info("ARQUIVO CSV GERADO COM SUCESSO!!");
            return csvOutputFile;
        } catch (IOException e) {
            throw new IOException("IOError " + e.getMessage());
        }
    }

    private List<String> convertToCsv (List<Transaction> reportData) {
        return reportData.stream()
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.toList());
    }

    private String escapeSpecialCharacters(Transaction data) {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(data.getReceiver().getFirstname())
                .add(data.getSent().getFirstname())
                .add(data.getAmount().toString());

        String joinedString = joiner.toString();

        return joinedString;
    }
}
