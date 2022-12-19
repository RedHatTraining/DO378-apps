package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.event.FraudScoreWasCalculated;
import com.redhat.training.event.HighRiskAccountDetected;
import com.redhat.training.event.LowRiskAccountDetected;
import org.eclipse.microprofile.reactive.messaging.*;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FraudProcessor {

    @Channel("low-risk")
    Emitter<LowRiskAccountDetected> lowRiskEmitter;

    @Channel("high-risk")
    Emitter<HighRiskAccountDetected> highRiskEmitter;

    @Incoming("new-bank-account-in")
    @Outgoing("fraud-scores")
    public Message<FraudScoreWasCalculated> calculateScore(BankAccountWasCreated event) {
        return Message.of(
                new FraudScoreWasCalculated(
                        event.id,
                        calculateFraudScore(event.balance)
                )
        );
    }

    @Incoming("fraud-scores")
    public void sendEventNotifications(Message<FraudScoreWasCalculated> message) {
        FraudScoreWasCalculated event = message.getPayload();

        if (event.score > 50) {
            highRiskEmitter.send(new HighRiskAccountDetected(event.bankAccountId));
        } else if (event.score > 20) {
            lowRiskEmitter.send(new LowRiskAccountDetected(event.bankAccountId));
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
