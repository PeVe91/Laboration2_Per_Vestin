package org.example;

import java.time.LocalDateTime;

public record Transaction(TransactionType type, LocalDateTime dateTime, String category, double amount) {}
