package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.BudgetService.*;

public class Menu {

//--------------------MAIN--------------------
    static void main() {

        Repository<Transaction> repository = new Repository<>();
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            String choice = IO.readln().trim();

            switch (choice) {
                case "1":
                    handleAddTransaction(repository);
                    break;
                case "2":
                    handleDisplayAllTransactions(repository);
                    break;
                case "3":
                    handleDisplayBalanceAndCategorySummaries(repository);
                    break;
                case "4":
                    handleFilterTransactions(repository);
                    break;
                case "e":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }

        }

    }

//--------------------HANDLERS--------------------
    private static void handleAddTransaction(Repository<Transaction> repository) {

        TransactionType type = readType();

        String category = readCategory();

        double amount = readAmount();

        Transaction transaction = new Transaction(type,  category, amount);
        repository.add(transaction);

        System.out.println("\nTransaction added successfully!");
    }

    private static void handleDisplayAllTransactions(Repository<Transaction> repository) {
        repository.getAll().forEach(System.out::println);
    }

    private static void handleDisplayBalanceAndCategorySummaries(Repository<Transaction> repository) {
        System.out.printf("Total balance: %.2f kr%n",
                getTotalBalance(repository.getAll()));
        System.out.println();

        Map<String, Double> categorySummaries =
                getCategorySummaries(repository.getAll());

        categorySummaries.forEach
                ((key, value) -> System.out.printf
                        ("%s: %.2f kr%n", key, value));

    }

    private static void handleFilterTransactions(Repository<Transaction> repository) {
        String choice;
        do {
            choice = IO.readln("Filter transactions by:\n1. Category\n2. Type\n").trim();

            switch (choice) {
                case "1":
                    handleFilterByCategory(repository);
                    break;
                case "2":
                    handleFilterByType(repository);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (!choice.equals("1") && !choice.equals("2"));
    }

    private static void handleFilterByCategory(Repository<Transaction> repository) {
        String category = readCategory();

        List<Transaction> filteredTransactions = filterByCategory(repository.getAll(), category);
        if (filteredTransactions.isEmpty()) {
            System.out.println("Category does not exist");
        } else {
            filteredTransactions.forEach(System.out::println);
        }
    }

    private static void handleFilterByType(Repository<Transaction> repository) {
        TransactionType type = readType();

        List<Transaction> filteredTransactions = filterByType(repository.getAll(), type);

        if (filteredTransactions.isEmpty()) {
            System.out.println("There are no transactions of this type");
        } else {
            filteredTransactions.forEach(System.out::println);
        }
    }

//--------------------INPUT-READERS--------------------
    private static double readAmount() {
        double amount;
        do {
            try {
                amount = Double.parseDouble(IO.readln("Amount: ")
                        .replaceAll("\\s+", "")
                        .replace(",", "."));
                if (amount <= 0) {
                    throw new InvalidTransactionException("Amount must be positive");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (InvalidTransactionException e) {
                System.out.println(e.getMessage());
            }
        } while (true);
        return amount;
    }

    private static String readCategory() {
        String category;
        do {
            category = IO.readln("Category: ").trim().toLowerCase();

            if (category.isBlank()) {
                System.out.println("Invalid category");
            }
        } while (category.isBlank());
        return category;
    }

    private static TransactionType readType() {
        do {
            String input = IO.readln("Choose transaction type:\n1. Income\n2. Expense\n").trim();

            switch (input) {
                case "1":
                    return TransactionType.INCOME;
                case "2":
                    return TransactionType.EXPENSE;
                default:
                    System.out.println("Invalid transaction type");
            }
        } while (true);
    }

//--------------------MENU-PRINTER--------------------
    static void printMenu() {
        System.out.println("""
                \n _______________________________
                |       Budget Service          |
                |-------------------------------|
                |   1. Add transaction          |
                |   2. Display all transactions |
                |   3. Display balance and      |
                |      category summaries       |
                |   4. Filter transactions      |
                |   5. Save to file             |
                |   e. Quit                     |
                |_______________________________|\n\n""");
    }

}
