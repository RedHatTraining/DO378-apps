package com.redhat.training.reactive;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.event.HighRiskAccountWasDetected;
import com.redhat.training.event.LowRiskAccountWasDetected;
import org.eclipse.microprofile.reactive.messaging.*;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class FraudProcessor {
    private static final Logger LOGGER = Logger.getLogger(FraudProcessor.class);

    @Channel("low-risk-alerts-out")
    Emitter<LowRiskAccountWasDetected> lowRiskEmitter;

    @Channel("high-risk-alerts-out")
    Emitter<HighRiskAccountWasDetected> highRiskEmitter;

    @Incoming("new-bank-accounts-in")
    public CompletionStage<Void> sendEventNotifications(
            Message<BankAccountWasCreated> message
    ) {
        BankAccountWasCreated event = message.getPayload();

        logBankAccountWasCreatedEvent(event);

        Integer fraudScore = calculateFraudScore(event.balance);

        logFraudScore(event.id, fraudScore);

        if (fraudScore > 50) {
            logEmitEvent("HighRiskAccountWasDetected", event.id);
            highRiskEmitter.send(
                new HighRiskAccountWasDetected(event.id)
            );
        } else if (fraudScore > 20) {
            logEmitEvent("LowRiskAccountWasDetected", event.id);
            lowRiskEmitter.send(
                new LowRiskAccountWasDetected(event.id)
            );
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

    private void logFraudScore(Long bankAccountId, Integer score) {
        LOGGER.infov(
                "Fraud score was calculated - ID: {0} Score: {1}",
                bankAccountId,
                score
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
