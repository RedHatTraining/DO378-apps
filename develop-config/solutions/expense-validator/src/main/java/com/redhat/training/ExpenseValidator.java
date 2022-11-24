package com.redhat.training;

import javax.inject.Inject;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpenseValidator {

    @Inject
    ExpenseConfiguration config;

    public void debugRanges() {
        config.debugMessage().ifPresent(System.out::println);
    }

    public boolean isValidAmount(int amount) {
        if (config.debugEnabled()) {
            debugRanges();
        }

        return amount >= config.rangeLow() && amount <= config.rangeHigh();
    }
}
