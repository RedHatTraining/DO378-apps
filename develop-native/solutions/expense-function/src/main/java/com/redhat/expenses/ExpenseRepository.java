package com.redhat.expenses;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.expenses.Expense.PaymentMethod;

@ApplicationScoped
public class ExpenseRepository {

    List<Expense> items = new ArrayList<>();

    public ExpenseRepository() {
        // Sample data
        items.add( new Expense( "example-expense-1", PaymentMethod.CREDIT_CARD, "83" ) );
        items.add( new Expense( "example-expense-21", PaymentMethod.CASH, "21" ) );
    }

    public void add( Expense expense ) {
        items.add( Expense.of( expense ) );
    }

    public void update( final Expense expense ) {
        Optional<Expense> possibleExpense = items.stream()
                .filter( e -> e.uuid.compareTo( expense.uuid ) == 0 )
                .findFirst();

        if ( possibleExpense.isPresent() ) {
            var current = possibleExpense.get();

            current.name = expense.name;
            current.amount = expense.amount;
            current.paymentMethod = expense.paymentMethod;
        }
    }

    public List<Expense> all() {
        return items;
    }
}
