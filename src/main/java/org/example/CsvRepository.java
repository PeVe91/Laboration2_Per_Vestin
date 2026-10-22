package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvRepository {

    public static void save(List<Transaction> transactions)
            throws IOException {

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

    public static List<Transaction> load()
            throws IOException, FileFormatException {
        Path homePath = Path.of(System.getProperty("user.home"));
        Path savePath = homePath.resolve("budgethanteraren");
        Path filePath = savePath.resolve("transactions.csv");

        List<String> lines = Files.readAllLines(filePath);
        List<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] lineArray = line.split(",", -1);

            if (lineArray.length != 4) {
                throw new FileFormatException(
                        "Transaction " + (i + 1) + " does not have all required parameters.");
            }

            try {
                LocalDateTime dateTime = LocalDateTime.parse(lineArray[0]);
                TransactionType type = TransactionType.valueOf(lineArray[1]);
                String category = lineArray[2];
                double amount = Double.parseDouble(lineArray[3]);

                if (category.isBlank() || amount <= 0) {
                    throw new FileFormatException(
                            "Transaction " + (i + 1) +
                                    " contains invalid category or amount.");
                }

                Transaction transaction =
                        new Transaction(dateTime, type, category, amount);
                transactions.add(transaction);

            } catch (java.time.format.DateTimeParseException
                     | IllegalArgumentException e) {
                throw new FileFormatException(
                        "Transaction " + (i + 1) +
                                " contains invalid date, type or amount.");
            }
        }
        return transactions;
    }
}