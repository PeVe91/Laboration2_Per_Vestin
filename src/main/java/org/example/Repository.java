package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Repository<T>{

    private static final Logger logger =
            Logger.getLogger(Repository.class.getName());

    private final List<T> transactions = new ArrayList<>();


//--------------------ADD TRANSACTION--------------------
    public void add(T transaction) {
        transactions.add(transaction);
        logger.info("Transaction added to repository");
    }

//--------------------GET ALL TRANSACTIONS--------------------
    public List<T> getAll() {
        return new ArrayList<>(transactions);
    }
//--------------------CLEAR TRANSACTIONS FROM LIST--------------------
    public void clear() {
        transactions.clear();
        logger.info("All transactions cleared from repository");
    }

}
