package com.redhat.training;

import com.redhat.training.event.BankAccountWasCreated;
import com.redhat.training.event.FraudScoreWasCalculated;
import com.redhat.training.reactive.FraudProcessor;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class FraudScoreCalculationTest {
    @Inject
    FraudProcessor application;

    @Test
    void testCalculateInvalidFraudScore() {
        BankAccountWasCreated triggerEvent = new BankAccountWasCreated(1L, 100L);
        FraudScoreWasCalculated expectedEvent = new FraudScoreWasCalculated(1L, -1);
        FraudScoreWasCalculated actualEvent = application.calculateScore(triggerEvent);

        assertEquals(expectedEvent.bankAccountId, actualEvent.bankAccountId);
        assertEquals(expectedEvent.score, actualEvent.score);
    }

    @Test
    void testCalculateLowFraudScore() {
        BankAccountWasCreated triggerEvent = new BankAccountWasCreated(2L, 5000L);
        FraudScoreWasCalculated expectedEvent = new FraudScoreWasCalculated(2L, 25);
        FraudScoreWasCalculated actualEvent = application.calculateScore(triggerEvent);

        assertEquals(expectedEvent.bankAccountId, actualEvent.bankAccountId);
        assertEquals(expectedEvent.score, actualEvent.score);
    }

    @Test
    void testCalculateHighFraudScore() {
        BankAccountWasCreated triggerEvent = new BankAccountWasCreated(3L, 40000L);
        FraudScoreWasCalculated expectedEvent = new FraudScoreWasCalculated(3L, 75);
        FraudScoreWasCalculated actualEvent = application.calculateScore(triggerEvent);

        assertEquals(expectedEvent.bankAccountId, actualEvent.bankAccountId);
        assertEquals(expectedEvent.score, actualEvent.score);
    }
}
