package com.redhat.training;

import com.redhat.training.event.FraudScoreWasCalculated;
import com.redhat.training.event.HighRiskAccountWasDetected;
import com.redhat.training.event.LowRiskAccountWasDetected;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySource;
import org.junit.jupiter.api.*;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FraudScoreNotificationTest {
    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    @Order(1)
    void testNotificationForNoneRiskAlerts() {
        InMemorySource<FraudScoreWasCalculated> fraudScoresIn = connector.source("in-memory-fraud-scores");
        InMemorySink<LowRiskAccountWasDetected> lowRiskAlertsOut = connector.sink("low-risk-alerts-out");
        InMemorySink<HighRiskAccountWasDetected> highRiskAlertsOut = connector.sink("high-risk-alerts-out");

        FraudScoreWasCalculated fraudScoreEvent = new FraudScoreWasCalculated(30L, -1);
        fraudScoresIn.send(fraudScoreEvent);

        Assertions.assertEquals(0, lowRiskAlertsOut.received().size());
        Assertions.assertEquals(0, highRiskAlertsOut.received().size());
    }

    @Test
    @Order(2)
    void testNotificationForLowRiskAlerts() {
        InMemorySource<FraudScoreWasCalculated> fraudScoresIn = connector.source("in-memory-fraud-scores");
        InMemorySink<LowRiskAccountWasDetected> lowRiskAlertsOut = connector.sink("low-risk-alerts-out");

        FraudScoreWasCalculated fraudScoreEvent = new FraudScoreWasCalculated(10L, 25);
        fraudScoresIn.send(fraudScoreEvent);

        Assertions.assertEquals(1, lowRiskAlertsOut.received().size());
    }

    @Test
    @Order(3)
    void testNotificationForHighRiskAlerts() {
        InMemorySource<FraudScoreWasCalculated> fraudScoresIn = connector.source("in-memory-fraud-scores");
        InMemorySink<HighRiskAccountWasDetected> highRiskAlertsOut = connector.sink("high-risk-alerts-out");

        FraudScoreWasCalculated fraudScoreEvent = new FraudScoreWasCalculated(20L, 75);
        fraudScoresIn.send(fraudScoreEvent);

        Assertions.assertEquals(1, highRiskAlertsOut.received().size());
    }
}
