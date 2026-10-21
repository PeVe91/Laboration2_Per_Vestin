package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.TransactionType.EXPENSE;
import static org.example.TransactionType.INCOME;
import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    @DisplayName("Should add transaction to list.")
    void addShouldStoreTransactionInList() {
        Transaction transaction = new Transaction(EXPENSE,
                "Tanka", 750.0);
        Repository<Transaction> repository = new Repository<>();

        repository.add(transaction);

        assertEquals(1, repository.getAll().size());
    }
    @DisplayName("Should return *all* transactions from list.")
    @Test
    void getAllShouldReturnAllTransactions() {
        Repository<Transaction> repository = new Repository<>();

        Transaction transaction = new Transaction(EXPENSE,
                "Tanka", 750.0);
        Transaction transaction2 = new Transaction(INCOME,
                "Lön", 35000);
        Transaction transaction3 = new Transaction(EXPENSE,
                "Kläder", 200);

        repository.add(transaction);
        repository.add(transaction2);
        repository.add(transaction3);

        assertEquals(3, repository.getAll().size());
    }
    @DisplayName("Should return copy of list instead of original.")
    @Test
    void getAllShouldReturnCopyListOfTransactions() {
        Repository<Transaction> repository = new Repository<>();

        Transaction transaction = new Transaction(EXPENSE,
                "Tanka", 750.0);
        Transaction transaction2 = new Transaction(INCOME,
                "Lön", 35000);
        Transaction transaction3 = new Transaction(EXPENSE,
                "Kläder", 200);

        repository.add(transaction);
        repository.add(transaction2);
        repository.add(transaction3);

        List<Transaction> temp = repository.getAll();
        temp.clear();
        assertEquals(3, repository.getAll().size());
    }

}