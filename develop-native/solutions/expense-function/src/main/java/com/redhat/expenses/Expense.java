package com.redhat.expenses;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.UUID;

public class Expense {

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    public UUID uuid;
    public String name;

    public LocalDateTime creationDate;
    public PaymentMethod paymentMethod;
    public BigDecimal amount;

    public Expense() {
    }

    public Expense( UUID uuid, String name, LocalDateTime creationDate, PaymentMethod paymentMethod, String amount ) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal( amount );
    }

    public Expense( String name, PaymentMethod paymentMethod, String amount ) {
        this( UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount );
    }

    public static Expense of( Expense e ) {
        return new Expense( e.name, e.paymentMethod, e.amount.toString() );
    }

}
