package com.redhat.training.event;

public class BankAccountWasCreated {
    public Long id;
    public Long balance;

    public BankAccountWasCreated() {}

    public BankAccountWasCreated(Long id, Long balance) {
        this.id = id;
        this.balance = balance;
    }
}
