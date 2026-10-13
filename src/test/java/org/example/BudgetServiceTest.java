package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.example.BudgetService.getTotalBalance;
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

        assertEquals(1999.10, getTotalBalance(transactions));


    }

    @Test
    void getCategorySummaries() {
    }
}