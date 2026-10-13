package org.example;

import java.util.List;

public class BudgetService {

    public static double getTotalBalance(List<Transaction> transactions) {
        double total = 0.0;

        for (Transaction transaction : transactions) {
            total += transaction.amount() * transaction.type().getMultiplier();
        }

        return total;
    }

}
