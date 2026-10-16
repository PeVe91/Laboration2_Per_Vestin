package org.example;

import java.util.ArrayList;
import java.util.List;

public class Repository<T>{

    private final List<T> transactions = new ArrayList<>();


//--------------------ADD TRANSACTION--------------------
    public void add(T transaction) {
        transactions.add(transaction);
    }

//--------------------GET ALL TRANSACTIONS--------------------
    public List<T> getAll() {
        return new ArrayList<>(transactions);
    }

}
