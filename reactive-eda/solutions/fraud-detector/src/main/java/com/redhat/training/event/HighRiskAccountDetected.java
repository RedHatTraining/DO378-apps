package com.redhat.training.event;

public class HighRiskAccountDetected {
    public Long bankAccountId;

    public HighRiskAccountDetected() {}

    public HighRiskAccountDetected(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
