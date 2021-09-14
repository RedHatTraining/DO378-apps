package org.acme.rest.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.json.bind.annotation.JsonbCreator;


public class Expense {

    enum PaymentMethod {
        CASH, CREDIT_CARD, DEBIT_CARD,
    }

    private UUID uuid;
    private String name;
    private LocalDateTime creationDate;
    private PaymentMethod paymentMethod;
    private BigDecimal amount;


    public Expense(UUID uuid, String name, LocalDateTime creationDate,
            PaymentMethod paymentMethod, String amount) {
        this.uuid = uuid;
        this.name = name;
        this.creationDate = creationDate;
        this.paymentMethod = paymentMethod;
        this.amount = new BigDecimal(amount);
    }

    public Expense(String name, PaymentMethod paymentMethod, String amount) {
        this(UUID.randomUUID(), name, LocalDateTime.now(), paymentMethod, amount);
    }

    @JsonbCreator
    public static Expense of(String name, PaymentMethod paymentMethod, String amount) {
        return new Expense(name, paymentMethod, amount);
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}