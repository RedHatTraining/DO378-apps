package com.redhat.training;

import com.redhat.training.event.SpeakerWasCreated;
import com.redhat.training.model.Affiliation;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import org.junit.jupiter.api.*;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeakerResourceTest {

    public static final Long TESTING_ID = 1L;
    public static final String TESTING_NAME = "A Person";
    public static final String TESTING_EMAIL = "test@example.com";

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    @Order(1)
    public void listOfSpeakersReturnsEmptyListOnStartup() {
        given()
        .when()
            .get("/speakers")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test
    @Order(2)
    public void creationOfSpeakersReturns201AndURI() {
        given()
            .body(generateRequestBody(Affiliation.RED_HAT))
            .contentType("application/json")
        .when()
            .post("/speakers")
        .then()
            .statusCode(201)
            .header("location", startsWith("http://"));

        given()
        .when()
            .get("/speakers")
        .then()
            .statusCode(200)
            .body("$.size()", is(1));
    }

    @Test
    @Order(3)
    public void creatingASpeakerFiresAnEvent() {
        InMemorySink<SpeakerWasCreated> eventsOut = connector.sink("new-speakers-out");

        given()
                .body(generateRequestBody(Affiliation.NONE))
                .contentType("application/json")
                .when()
                .post("/speakers")
                .then()
                .statusCode(201);

        var startTime = System.currentTimeMillis();

        // Providing some time to the test to process the event
        await().atMost(Duration.ofSeconds(10)).until(() -> System.currentTimeMillis() - startTime > 9000);

        SpeakerWasCreated queuedEvent = eventsOut.received().get(0).getPayload();

        Assertions.assertEquals(TESTING_ID, queuedEvent.id);
        Assertions.assertEquals(TESTING_NAME, queuedEvent.fullName);
        Assertions.assertEquals(TESTING_EMAIL, queuedEvent.email);
    }

    public static String generateRequestBody(Affiliation affiliation) {
        return "{" +
                "\"fullName\":\"" + TESTING_NAME + "\", " +
                "\"affiliation\":\"" + affiliation + "\"," +
                "\"email\":\"" + TESTING_EMAIL + "\"" +
                "}";
    }
}
