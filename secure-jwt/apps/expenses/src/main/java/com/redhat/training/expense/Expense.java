package com.redhat.training.expense;

import java.util.UUID;
import java.time.LocalDateTime;

public class Expense {
    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    public UUID uuid;
    public String name;
    public LocalDateTime creationDate;
    public PaymentMethod paymentMethod;
    public double amount;
    public String username;

    public Expense() {
    }

    public Expense( UUID uuid, String name, LocalDateTime creationDate, PaymentMethod paymentMethod, double amount, String username ) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.username = username;
    }

    public Expense( String name, double amount, String username ) {
        this( UUID.randomUUID(), name, LocalDateTime.now(), PaymentMethod.CASH, amount, username );
    }

}
