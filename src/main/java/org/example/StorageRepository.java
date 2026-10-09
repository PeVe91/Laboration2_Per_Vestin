package org.example;

import java.util.ArrayList;
import java.util.List;

public class StorageRepository<T>{



    private final List<T> transactions = new ArrayList<>();

    public void add(T transaction) {
        transactions.add(transaction);
    }
    public List<T> getAll() {
        return new ArrayList<>(transactions);
    }
    public int getTransactionCount() {
        return transactions.size();
    }

}
