package org.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.example.BudgetService.*;
import static org.example.CsvRepository.load;
import static org.example.CsvRepository.save;

public class Menu {

    private static final Logger logger =
            Logger.getLogger(Menu.class.getName());

//--------------------MAIN--------------------
    static void main() {

        Repository<Transaction> repository = new Repository<>();
        boolean isRunning = true;

        logger.info("Program initialised");

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
                case "5":
                    handleSaveToFile(repository);
                    break;
                case "6":
                    handleLoadFile(repository);
                    break;
                case "e":
                    logger.info("Program terminated by user");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice");
                    logger.warning("Invalid menu choice");
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
        logger.info("Transaction added successfully");
    }

    private static void handleDisplayAllTransactions(Repository<Transaction> repository) {
        if (repository.getAll().isEmpty()) {
            System.out.println("No transactions found");
            logger.info("No transactions found");
        } else {
            repository.getAll().forEach(System.out::println);
        }
    }

    private static void handleDisplayBalanceAndCategorySummaries(Repository<Transaction> repository) {
        System.out.printf("Total balance: %.2f kr%n",
                getTotalBalance(repository.getAll()));

        Map<String, Double> categorySummaries =
                getCategorySummaries(repository.getAll());
        if  (categorySummaries.isEmpty()) {
            System.out.println();
            System.out.println("No transactions found");
            logger.info("No transactions found");
        } else {
            System.out.println();
            categorySummaries.forEach
                    ((key, value) -> System.out.printf
                            ("%s: %.2f kr%n", key, value));
        }

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
                    logger.warning("Invalid menu choice");
            }
        } while (!choice.equals("1") && !choice.equals("2"));
    }

    private static void handleFilterByCategory(Repository<Transaction> repository) {
        String category = readCategory();

        List<Transaction> filteredTransactions = filterByCategory(repository.getAll(), category);
        if (filteredTransactions.isEmpty()) {
            System.out.println("Category does not exist");
            logger.info("Category does not exist");
        } else {
            filteredTransactions.forEach(System.out::println);
        }
    }

    private static void handleFilterByType(Repository<Transaction> repository) {
        TransactionType type = readType();

        List<Transaction> filteredTransactions = filterByType(repository.getAll(), type);

        if (filteredTransactions.isEmpty()) {
            System.out.println("There are no transactions of this type");
            logger.info("There are no transactions of this type");
        } else {
            filteredTransactions.forEach(System.out::println);
        }
    }

    private static void handleSaveToFile(Repository<Transaction> repository) {

        try {
            save(repository.getAll());
            System.out.println("Transactions saved successfully!");
            logger.info("Transactions saved successfully");
        } catch (IOException e) {
            System.out.println("Could not save transactions: "
                    + e.getMessage());
            logger.severe(e.getMessage());
        }
    }

    private static void handleLoadFile(Repository<Transaction> repository) {

        try {
            List<Transaction> loadedList = load();
            repository.clear();
            if (loadedList.isEmpty()){
                System.out.println("No transactions found");
                logger.info("No transactions found");
            } else {
                for (Transaction transaction : loadedList) {
                    repository.add(transaction);
                }
                System.out.println("Transactions loaded successfully!");
                logger.info("Transactions loaded successfully");
                for (Transaction transaction : repository.getAll()) {
                    System.out.println(transaction);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load transactions: " + e.getMessage());
            logger.severe(e.getMessage());
        } catch (FileFormatException e) {
            System.out.println("Invalid CSV file: " + e.getMessage());
            logger.severe(e.getMessage());
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
                logger.warning("Invalid number.");
            } catch (InvalidTransactionException e) {
                System.out.println(e.getMessage());
                logger.warning("Negative amount or zero");
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
                    logger.info("Invalid category");
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
                    logger.warning("Invalid transaction type");
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
                |   6. Load file                |
                |   e. Quit                     |
                |_______________________________|\n\n""");
    }

}
