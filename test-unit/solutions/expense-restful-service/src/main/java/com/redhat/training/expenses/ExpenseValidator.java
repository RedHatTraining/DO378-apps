package com.redhat.training.expenses;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ExpenseValidator {

    @Inject
    ExpenseConfiguration config;

    public boolean isValid( Expense expense ) {
        return amountIsValid( expense );
    }

    private boolean amountIsValid( Expense expense ) {
        return expense.amount.compareTo( config.maxAmount() ) <= 0;
    }
}
