package org.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BudgetService {

//--------------------GET TOTAL BALANCE--------------------
    public static double getTotalBalance(List<Transaction> transactions) {
        return transactions.stream()
                .mapToDouble(transaction ->
                        transaction.amount() * transaction.type()
                                .getMultiplier()).sum();
    }

//--------------------SUMMARIZE CATEGORIES--------------------
    public static Map<String, Double>
    getCategorySummaries(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy
                (Transaction::category,
                        Collectors.summingDouble(
                                transaction ->
                                        transaction.amount()
                                        * transaction.type()
                                                .getMultiplier())));
    }
//--------------------GET TRANSACTIONS FILTERED BY CATEGORY--------------------
    public static List<Transaction> filterByCategory
    (List<Transaction> transactions, String category) {

        return transactions.stream()
                .filter(transaction ->
                        category.equals(transaction.category()))
                .collect(Collectors.toList());
    }

//--------------------GET TRANSACTIONS FILTERED BY TYPE--------------------
    public static List<Transaction> filterByType
            (List<Transaction> transactions, TransactionType type) {

        return transactions.stream()
                .filter(transaction ->
                        type.equals(transaction.type()))
                .collect(Collectors.toList());
    }

}
