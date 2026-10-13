package org.example;

import java.util.ArrayList;
import java.util.List;

public class Repository<T>{

    private final List<T> transactions = new ArrayList<>();



    public void add(T transaction) {
        transactions.add(transaction);
    }

    public List<T> getAll() {
        return new ArrayList<>(transactions);
    }

}
