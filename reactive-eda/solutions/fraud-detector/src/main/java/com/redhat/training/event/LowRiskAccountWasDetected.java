package com.redhat.training.event;

public class LowRiskAccountWasDetected {
    public Long bankAccountId;

    public LowRiskAccountWasDetected() {}

    public LowRiskAccountWasDetected(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
