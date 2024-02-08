package com.redhat.training.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Optional;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Type;

// TODO: Add @Entity annotation and extend PanacheEntity
@Entity
public class Expense extends PanacheEntity {

    public enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    @Type(type = "uuid-char")
    @NotNull
    public UUID uuid;
    public String name;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime creationDate;
    public PaymentMethod paymentMethod;
    public BigDecimal amount;

    // TODO: Add many-to-one relationship between expense and associate
    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "associate_id", insertable = false, updatable = false)
    public Associate associate;

    // TODO: Annotate the associateId with @Column
    @Column(name = "associate_id")
    public Long associateId;

    // TODO: Add a no-argument constructor
    public Expense() {
    }

    public Expense(UUID uuid, String name, LocalDateTime creationDate,
            PaymentMethod paymentMethod, String amount, Associate associate) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal(amount);
        this.associate = associate;
        // TODO: Add associateId association
        this.associateId = associate.id;
    }

    public Expense(String name, PaymentMethod paymentMethod, String amount, Associate associate) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount, associate);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, String amount, Long associateId) {

        // TODO: Update regarding the new relationship
        return Associate.<Associate>findByIdOptional(associateId)
            .map(associate -> new Expense(name, paymentMethod, amount, associate))
            .orElseThrow(RuntimeException::new);
    }

    // TODO: Add update() method
    public static void update(final Expense expense) throws RuntimeException {
        Optional<Expense> previousExpense = Expense.findByIdOptional(expense.id);

        previousExpense.ifPresentOrElse((updatedExpense) -> {
            updatedExpense.uuid = expense.uuid;
            updatedExpense.name = expense.name;
            updatedExpense.amount = expense.amount;
            updatedExpense.paymentMethod = expense.paymentMethod;
            updatedExpense.persist();
        }, () -> {
            throw new WebApplicationException( Response.Status.NOT_FOUND );
        });
    }

}
