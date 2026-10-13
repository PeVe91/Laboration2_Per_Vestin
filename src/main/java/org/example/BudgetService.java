package org.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetService {

    public static double getTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(transaction ->
                        transaction.amount() * transaction.type()
                                .getMultiplier()).sum();
    }

    public static Map<String, Double> getCategorySummaries(List<Transaction> transactions) {
        return transactions.stream().collect(Collectors.groupingBy
                (Transaction::category, Collectors.summingDouble(
                                transaction -> transaction.amount()
                                        * transaction.type().getMultiplier())));
    }

}
