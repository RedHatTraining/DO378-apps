package com.redhat.training.expense;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Entity;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
public class Expense extends PanacheEntity {

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    public UUID uuid;

    public String name;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime creationDate;

    public PaymentMethod paymentMethod;

    public double amount;

    public Expense() {
    }

    public Expense(UUID uuid, String name, LocalDateTime creationDate, PaymentMethod paymentMethod, double amount) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    public Expense(String name, PaymentMethod paymentMethod, double amount) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, double amount) {
        return new Expense(name, paymentMethod, amount);
    }

    public static void update(final Expense expense) {
        Optional<Expense> previous = Expense.findByIdOptional(expense.id);

        previous.ifPresentOrElse((update) -> {
            update.uuid = expense.uuid;
            update.name = expense.name;
            update.amount = expense.amount;
            update.paymentMethod = expense.paymentMethod;

            update.persist();
        }, () -> {
            throw new NotFoundException();
        });
    }

}