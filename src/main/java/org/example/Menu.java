package org.example;

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
                    System.exit(0);
            }


        }

    }

    private static void handleAddTransaction(Repository<Transaction> repository) {

        String transTypeInput = IO.readln().trim();
        TransactionType transType = TransactionType.valueOf(transTypeInput);


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
