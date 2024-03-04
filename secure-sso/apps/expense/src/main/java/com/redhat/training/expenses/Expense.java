package com.redhat.training.expenses;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import org.hibernate.annotations.Type;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Expense extends PanacheEntityBase {

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    @Id
    public UUID uuid;
    public String name;

    public PaymentMethod paymentMethod;
    public BigDecimal amount;

    public Expense() {
    }

    public Expense( UUID uuid, String name, PaymentMethod paymentMethod, String amount ) {
        this.uuid = uuid;
        this.name = name;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal( amount );
    }

    public Expense( String name, PaymentMethod paymentMethod, String amount ) {
        this( UUID.randomUUID(), name, paymentMethod, amount );
    }

    public static Expense of( String name, PaymentMethod paymentMethod, String amount ) {
        return new Expense( name, paymentMethod, amount );
    }

    public static void update( final Expense expense ) {
        Optional<Expense> previous = Expense.findByIdOptional( expense.uuid );

        previous.ifPresentOrElse( ( update ) -> {
            update.uuid = expense.uuid;
            update.name = expense.name;
            update.amount = expense.amount;
            update.paymentMethod = expense.paymentMethod;

            update.persist();
        }, () -> {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        } );
    }

}