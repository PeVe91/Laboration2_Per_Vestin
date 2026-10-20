package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvRepository {

    public void save(List<Transaction> transactions) throws IOException {

        List<String> transactionsString = transactions.stream()
                .map(transaction -> transaction.dateTime() + ","
                        + transaction.type() + ","
                        + transaction.category() + ","
                        + transaction.amount())
                .toList();

        Path homePath = Path.of(System.getProperty("user.home"));

        Path savePath = homePath.resolve("budgethanteraren");

        Path filePath = savePath.resolve("transactions.csv");

        Files.createDirectories(savePath);

        Files.write(
                filePath,
                transactionsString,
                java.nio.charset.StandardCharsets.UTF_8
        );
    }
}