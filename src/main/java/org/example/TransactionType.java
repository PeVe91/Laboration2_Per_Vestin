package org.example;

public enum TransactionType {
    INCOME(1),
    EXPENSE(-1);

    private final int multiplier;

    TransactionType(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }

}
