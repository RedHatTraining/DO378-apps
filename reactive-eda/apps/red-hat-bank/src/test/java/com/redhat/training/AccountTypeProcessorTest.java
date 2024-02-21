package com.redhat.training;

import com.redhat.training.reactive.AccountTypeProcessor;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import org.junit.jupiter.api.*;

import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountTypeProcessorTest {
    @Inject
    @Any
    InMemoryConnector connector;

    @Inject
    AccountTypeProcessor application;

    @BeforeAll
    public static void setup() {

    }

    @Test
    public void lowBalanceAssignsRegularType() {
        Assertions.assertEquals("regular", application.calculateAccountType(50L));
    }

    @Test
    public void highBalanceAssignsPremiumType() {
        Assertions.assertEquals("premium", application.calculateAccountType(10000050L));
    }
}
