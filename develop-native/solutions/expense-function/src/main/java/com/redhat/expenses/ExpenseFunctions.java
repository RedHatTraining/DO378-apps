package com.redhat.expenses;

import java.util.List;

import javax.inject.Inject;

import io.quarkus.funqy.Funq;

public class ExpenseFunctions {

    @Inject
    ExpenseRepository repository;

    @Funq
    public List<Expense> expenses() {
        return repository.all();
    }

    @Funq
    public void createExpense( Expense expense ) {
        repository.add( expense );
    }
}