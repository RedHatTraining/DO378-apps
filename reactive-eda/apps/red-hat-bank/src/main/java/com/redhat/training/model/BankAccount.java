package com.redhat.training.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@Cacheable
public class BankAccount extends PanacheEntity {

    public Long balance;

    public String type;

    public BankAccount() {
    }

    public BankAccount(Long balance, String type) {
        this.balance = balance;
        this.type = type;
    }
}
