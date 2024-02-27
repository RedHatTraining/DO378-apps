package com.redhat.training.expense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpensesService {

    private List<Expense> expenses;

    ExpensesService() {
        expenses = new ArrayList<>();
        expenses.add( new Expense( "Expense 1", 43.5, "patricia@example.com" ) );
        expenses.add( new Expense( "Expense 1", 10.0, "joel@example.com" ) );
        expenses.add( new Expense( "Expense 1", 24.2, "joel@example.com" ) );
    }

    List<Expense> list() {
        return expenses;
    }

    List<Expense> listByOwner( String username ) {
        return expenses
                .stream()
                .filter( expense -> expense.username.equalsIgnoreCase( username ) )
                .collect( Collectors.toList() );
    }
}
