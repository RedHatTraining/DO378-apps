package com.redhat.training;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class ExpenseService {
    private Set<Expense> expenses = Collections.newSetFromMap(Collections.synchronizedMap(new HashMap<>()));

    @PostConstruct
    void init() {
        expenses.add(new Expense("Quarkus for Spring Developers", Expense.PaymentMethod.DEBIT_CARD, "10.00"));
        expenses.add(new Expense("OpenShift for Developers, Second Edition", Expense.PaymentMethod.DEBIT_CARD, "15.00"));
    }

    public Set<Expense> list() {
        simulateMaxDelayInSeconds(1);

        return expenses;
    }

    public Expense create(Expense expense) {
        simulateMaxDelayInSeconds(5);

        expenses.add(expense);

        return expense;
    }

   public boolean delete(UUID uuid) {
        return expenses.removeIf(expense -> expense.getUuid().equals(uuid));
    }

    public void update(Expense expense) {
        delete(expense.getUuid());
        create(expense);
    }

    public boolean exists(UUID uuid) {
        return expenses.stream().anyMatch(exp -> exp.getUuid().equals(uuid));
    }

    private void simulateMaxDelayInSeconds(int maxDelay) {
        int seconds = new Random().nextInt(maxDelay +1);

        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
