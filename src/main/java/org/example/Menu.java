package org.example;

public class Menu {

    StorageRepository<Transaction> repository = new StorageRepository<>();

    static void main() {

        printMenu();

    }

    static void printMenu() {
        System.out.println("""
                Budgethanteraren ================
                1. Add transaction
                2. Display all transactions
                3. Display balance and category summaries
                4. Filter transactions
                5. Save to file
                e. Quit""");
    }

}
