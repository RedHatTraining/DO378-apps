package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.event.FraudScoreWasCalculated;
import com.redhat.training.event.HighRiskAccountWasDetected;
import com.redhat.training.event.LowRiskAccountWasDetected;
import io.smallrye.reactive.messaging.annotations.Merge;
import org.eclipse.microprofile.reactive.messaging.*;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class FraudProcessor {
    private static final Logger LOGGER = Logger.getLogger(FraudProcessor.class);

    @Channel("low-risk-alerts-out")
    Emitter<LowRiskAccountWasDetected> lowRiskEmitter;

    @Channel("high-risk-alerts-out")
    Emitter<HighRiskAccountWasDetected> highRiskEmitter;

    @Incoming("new-bank-accounts-in")
    @Outgoing("in-memory-fraud-scores")
    public FraudScoreWasCalculated calculateScore(BankAccountWasCreated event) {
        logBankAccountWasCreatedEvent(event);

        return new FraudScoreWasCalculated(
                event.id,
                calculateFraudScore(event.balance)
        );
    }

    @Incoming("in-memory-fraud-scores")
    @Merge
    public CompletionStage<Void> sendEventNotifications(
            Message<FraudScoreWasCalculated> message
    ) {
        FraudScoreWasCalculated event = message.getPayload();

        logFraudScoreWasCalculatedEvent(event);

        if (event.score > 50) {
            logEmitEvent("HighRiskAccountWasDetected", event.bankAccountId);
            highRiskEmitter.send(new HighRiskAccountWasDetected(event.bankAccountId));
        } else if (event.score > 20) {
            logEmitEvent("LowRiskAccountWasDetected", event.bankAccountId);
            lowRiskEmitter.send(new LowRiskAccountWasDetected(event.bankAccountId));
        }

        return message.ack();
    }

    private Integer calculateFraudScore(Long amount) {
        if (amount > 25000) {
            return 75;
        } else if (amount > 3000) {
            return 25;
        }

        return -1;
    }

    private void logBankAccountWasCreatedEvent(BankAccountWasCreated event) {
        LOGGER.infov(
                "Received BankAccountWasCreated - ID: {0} Balance: {1}",
                event.id,
                event.balance
        );
    }

    private void logFraudScoreWasCalculatedEvent(FraudScoreWasCalculated event) {
        LOGGER.infov(
                "Processing FraudScoreWasCalculated - ID: {0} Score: {1}",
                event.bankAccountId,
                event.score
        );
    }

    private void logEmitEvent(String eventName, Long bankAccountId) {
        LOGGER.infov(
                "Sending a {0} event for bank account #{1}",
                eventName,
                bankAccountId
        );
    }
}
