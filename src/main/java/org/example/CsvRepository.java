package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Files.write(filePath, transactionsString);
    }

    public List<Transaction> load() throws IOException {
        Path homePath = Path.of(System.getProperty("user.home"));
        Path savePath = homePath.resolve("budgethanteraren");
        Path filePath = savePath.resolve("transactions.csv");

        List<String> lines = Files.readAllLines(filePath);
        List<Transaction> transactions = new ArrayList<>();

        for (String line : lines) {
            String[] lineArray = line.split(",");

            LocalDateTime dateTime = LocalDateTime.parse(lineArray[0]);
            TransactionType type = TransactionType.valueOf(lineArray[1]);
            String category = lineArray[2];
            double amount = Double.parseDouble(lineArray[3]);

            Transaction transaction = new Transaction(dateTime, type, category, amount);
            transactions.add(transaction);
        }
        return transactions;
    }
}