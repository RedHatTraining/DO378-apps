package com.redhat.training;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpenseValidator {

    private static final boolean DEBUG_ENABLED = true;

    private static final int RANGE_HIGH = 1000;

    private static final int RANGE_LOW = 250;

    public void debugRanges() {
        System.out.println("Range - High: " + RANGE_HIGH);
        System.out.println("Range - Low: " + RANGE_LOW);
    }

    public boolean isValidAmount(int amount) {
        if (DEBUG_ENABLED) {
            debugRanges();
        }

        return amount >= RANGE_LOW && amount <= RANGE_HIGH;
    }
}
