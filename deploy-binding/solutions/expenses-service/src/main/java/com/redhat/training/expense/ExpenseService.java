package com.redhat.training.expense;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ExpenseService {

    public final double MINIMUM_AMOUNT = 500;

    public List<Expense> list() {
        return Expense.listAll();
    }

    public Expense create(Expense expense) {
        Expense newExpense = Expense.of(expense.name, expense.paymentMethod, expense.amount);

        newExpense.persist();

        return newExpense;
    }

    public void delete(UUID uuid) {
        long numExpensesDeleted = Expense.delete("uuid", uuid);

        if (numExpensesDeleted == 0) {
            throw new NotFoundException();
        }
    }

    public void update(Expense newExpense) {
        Expense originalExpense = Expense.find("uuid", newExpense.uuid).firstResult();

        newExpense.id = originalExpense.id;

        Expense.update(newExpense);
    }

    public boolean exists(UUID uuid) {
        return Expense.find("uuid", uuid).count() == 1;
    }

    public boolean meetsMinimumAmount(double amount) {
        return amount >= MINIMUM_AMOUNT;
    }
}