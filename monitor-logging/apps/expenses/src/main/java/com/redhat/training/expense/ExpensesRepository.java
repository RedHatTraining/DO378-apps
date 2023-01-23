package com.redhat.training.expense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpensesRepository {

    private List<Expense> expenses;

    ExpensesRepository() {
        expenses = new ArrayList<>();
        expenses.add( new Expense( "pat-1", 43.5, "patricia@example.com" ) );
        expenses.add( new Expense( "joel-2", 10.0, "joel@example.com" ) );
        expenses.add( new Expense( "joel-3", 24.2, "joel@example.com" ) );
    }

    List<Expense> list() {
        return expenses;
    }

    Expense getByName( String name ) throws ExpenseNotFoundException {
        var found = expenses
                .stream()
                .filter( expense -> expense.name.equalsIgnoreCase( name ) )
                .collect( Collectors.toList() );

        try {
            return found.get( 0 );
        } catch( IndexOutOfBoundsException e ) {
            throw new ExpenseNotFoundException( name );
        }
    }
}
