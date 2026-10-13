package org.example;

import java.time.LocalDateTime;

public record Transaction(
        LocalDateTime dateTime,
        TransactionType type,
        String category,
        double amount) {

    public Transaction(TransactionType type, String category, double amount) {
        this(LocalDateTime.now(), type, category, amount);
    }
}
