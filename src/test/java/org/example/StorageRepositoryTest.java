package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.example.TransactionType.EXPENSE;
import static org.junit.jupiter.api.Assertions.*;

class StorageRepositoryTest {

    @Test
    @DisplayName("Should add transaction to list.")
    void addShouldStoreTransactionInList() {
        Transaction transaction = new Transaction(EXPENSE, LocalDateTime.now(),
                "Tanka", 750.00);
        Repository<Transaction> repository = new Repository<>();

        repository.add(transaction);

        assertEquals(1, repository.getTransactionCount());
    }
    @DisplayName("Should return *all* transactions from list.")
    @Test
    void getAllShouldReturnAllTransactions() {
    }
    @DisplayName("Should return copy of list instead of original.")
    @Test
    void getAllShouldReturnCopyListOfTransactions() {
    }

}