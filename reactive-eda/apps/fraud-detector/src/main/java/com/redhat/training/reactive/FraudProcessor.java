package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.event.FraudScoreWasCalculated;
import com.redhat.training.event.HighRiskAccountWasDetected;
import com.redhat.training.event.LowRiskAccountWasDetected;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FraudProcessor {

    @Channel("low-risk-alerts-out")
    Emitter<LowRiskAccountWasDetected> lowRiskEmitter;

    @Channel("high-risk-alerts-out")
    Emitter<HighRiskAccountWasDetected> highRiskEmitter;

    @Incoming("new-bank-accounts-in")
    @Outgoing("in-memory-fraud-scores")
    public Message<FraudScoreWasCalculated> calculateScore(BankAccountWasCreated event) {
        return Message.of(
                new FraudScoreWasCalculated(
                        event.id,
                        calculateFraudScore(event.balance)
                )
        );
    }

    @Incoming("in-memory-fraud-scores")
    public void sendEventNotifications(Message<FraudScoreWasCalculated> message) {
        FraudScoreWasCalculated event = message.getPayload();

        if (event.score > 50) {
            highRiskEmitter.send(new HighRiskAccountWasDetected(event.bankAccountId));
        } else if (event.score > 20) {
            lowRiskEmitter.send(new LowRiskAccountWasDetected(event.bankAccountId));
        } else {
            message.ack();
        }
    }

    private Integer calculateFraudScore(Long amount) {
        if (amount > 25000) {
            return 75;
        } else if (amount > 3000) {
            return 25;
        } else {
            return -1;
        }
    }
}
