package com.redhat.training.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

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
    public BigDecimal amount;

    @Column(name = "associate_id")
    public Long associateId;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id", insertable = false, updatable = false)
    public Associate associate;

    public Expense() {
    }

    public Expense(UUID uuid, String name, LocalDateTime creationDate, PaymentMethod paymentMethod, String amount,
            Associate associate) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal(amount);
        this.associate = associate;
        this.associateId = associate.id;
    }

    public Expense(String name, PaymentMethod paymentMethod, String amount, Associate associate) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount, associate);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, String amount, Long associateId) {
        Optional<Associate> associateOpt = Associate.findByIdOptional(associateId);

        if(associateOpt.isPresent()){
            return new Expense(name, paymentMethod, amount, associateOpt.get());
        } else {
            throw new RuntimeException();
        }
    }

    public static void update(final Expense expense) throws RuntimeException{
        Optional<Expense> previousExpense = Expense.findByIdOptional(expense.id);

        previousExpense.ifPresentOrElse((updatedExpense) -> {
            updatedExpense.uuid = expense.uuid;
            updatedExpense.name = expense.name;
            updatedExpense.amount = expense.amount;
            updatedExpense.paymentMethod = expense.paymentMethod;
            updatedExpense.persist();
        }, () -> {
            throw new RuntimeException();
        });
    }

}