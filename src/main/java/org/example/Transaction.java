package org.example;

import java.time.LocalDateTime;

public record Transaction(LocalDateTime dateTime, TransactionType type, String category, double amount) {}
