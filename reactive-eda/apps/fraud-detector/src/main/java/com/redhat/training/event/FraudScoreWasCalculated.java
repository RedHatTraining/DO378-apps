package com.redhat.training.event;

public class FraudScoreWasCalculated {
    public Long bankAccountId;

    public Integer score;

    public FraudScoreWasCalculated() {}

    public FraudScoreWasCalculated(Long bankAccountId, Integer score) {
        this.bankAccountId = bankAccountId;
        this.score = score;
    }
}
