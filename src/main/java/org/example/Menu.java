package org.example;

import java.time.LocalDateTime;

public class Menu {


    static void main() {
        Repository<Transaction> repository = new Repository<>();
        boolean isRunning = true;

        while (isRunning) {
            printMenu();
            String choice = IO.readln().trim();

            switch (choice) {
                case "1":
                    handleAddTransaction(repository);
                default:
                    isRunning = false;
            }

        }

    }

    private static void handleAddTransaction(Repository<Transaction> repository) {

        TransactionType type;
        do {
            String transTypeInput = IO.readln("Income or expense: ").trim().toUpperCase();
            try {
                type = TransactionType.valueOf(transTypeInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transaction type");
            }
        } while (true);

        String category;
        do {
            category = IO.readln("Category: ").trim().toLowerCase();

            if (category.isBlank()) {
                System.out.println("Invalid category");
            }
        } while (category.isBlank());

        double amount;
        do {
            try {
                amount = Double.parseDouble(IO.readln("Amount: "));
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

        Transaction transaction = new Transaction(type,  category, amount);
        repository.add(transaction);
    }

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
