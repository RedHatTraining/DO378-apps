package com.redhat.training.expense;

public class ExpenseNotFoundException extends Exception {

    public ExpenseNotFoundException( String name ) {
        super( "Expense not found: " + name );
    }
}
