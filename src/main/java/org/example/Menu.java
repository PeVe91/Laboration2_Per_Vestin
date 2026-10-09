package org.example;

public class Menu {
    static void main() {

        printMenu();

    }

    static void printMenu() {
        System.out.println("""
                Budgethanteraren ================
                1. Lagg till transaktion
                2. Visa alla transaktioner
                3. Visa saldo och sammanstallning per kategori
                4. Filtrera transaktioner
                5. Spara till fil
                e. Avsluta""");
    }

}
