package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.BudgetService.*;
import static org.example.TransactionType.INCOME;
import static org.example.TransactionType.EXPENSE;
import static org.junit.jupiter.api.Assertions.*;

class BudgetServiceTest {

    @Test
    @DisplayName("Mixing incomes and expenses should result in correct balance")
    void mixingIncomesAndExpensesShouldYieldCorrectBalance() {

        List<Transaction> transactions = List.of(
                new Transaction(INCOME, "Mat", 230),
                new Transaction(EXPENSE, "Kläder", 230.90),
                new Transaction(INCOME, "Lön", 1000),
                new Transaction(INCOME, "Lön", 1000)
        );

        assertEquals(1999.10, getTotalBalance(transactions), 0.001);

    }

    @Test
    @DisplayName("Transactions in the same category should be combined")
    void transactionsInSameCategoryShouldBeCombined() {
        List<Transaction> transactions = List.of(
                new Transaction(EXPENSE, "Mat", 200),
                new Transaction(EXPENSE, "Mat", 100),
                new Transaction(INCOME, "Lön", 25000)
        );

        Map<String, Double> result = getCategorySummaries(transactions);

        assertEquals(2, result.size());

        assertEquals(-300, result.get("Mat"));
        assertEquals(25000, result.get("Lön"));

    }

    @Test
    @DisplayName("Only transactions of selected category should be returned")
    void onlyTransactionsOfTheSameCategoryShouldBeReturned() {
        List<Transaction> transactions = List.of(
                new Transaction(EXPENSE, "mat", 200),
                new Transaction(EXPENSE, "mat", 150),
                new Transaction(INCOME, "lön", 100)
        );

        List<Transaction> result = filterByCategory(transactions, "mat");

        assertEquals(2, result.size());

        assertTrue(result.stream().allMatch(
                transaction -> transaction
                        .category().equals("mat")));
    }

    @Test
    @DisplayName("Non-existent category input should return an empty list")
    void nonExistentCategoryShouldReturnAnEmptyList() {
        List<Transaction> transactions = List.of(
                new Transaction(INCOME, "lön", 100)
        );

        List<Transaction> result = filterByCategory(transactions, "mat");

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Only transactions of selected type should be returned")
    void onlyTransactionsOfTheSameTypeShouldBeReturned() {
        List<Transaction> transactions = List.of(
                new Transaction(EXPENSE, "mat", 200),
                new Transaction(EXPENSE, "kläder", 150),
                new Transaction(INCOME, "lön", 100)
        );

        List<Transaction> resultIncome = filterByType(transactions, INCOME);
        List<Transaction> resultExpense = filterByType(transactions, EXPENSE);

        assertEquals(1, resultIncome.size());
        assertTrue(resultIncome.stream().allMatch(
                transaction -> transaction
                        .type().equals(INCOME)));

        assertEquals(2, resultExpense.size());
        assertTrue(resultExpense.stream().allMatch(
                transaction -> transaction
                        .type().equals(EXPENSE)));
    }

    @Test
    @DisplayName("If no transactions of selected typ exist, an empty list should be returned")
    void emptyListShouldBeReturnWhenInputTransactionTypeIsAbsent() {
        List<Transaction> transactions = List.of();

        List<Transaction> result = filterByType(transactions, INCOME);

        assertTrue(result.isEmpty());
    }
}