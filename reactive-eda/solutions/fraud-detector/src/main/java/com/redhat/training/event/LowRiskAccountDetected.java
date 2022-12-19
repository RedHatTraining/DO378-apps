package com.redhat.training.event;

public class LowRiskAccountDetected {
    public Long bankAccountId;

    public LowRiskAccountDetected() {}

    public LowRiskAccountDetected(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
