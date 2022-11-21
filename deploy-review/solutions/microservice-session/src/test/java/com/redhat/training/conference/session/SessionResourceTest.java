package com.redhat.training.conference.session;

import com.redhat.training.conference.speaker.SpeakerServiceClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionResourceTest {

    @RestClient
    @Inject
    @InjectMock
    SpeakerServiceClient speakerService;

    @Test
    @Order(1)
    public void checkListOfSessionsIsEmpty() {
        given()
        .when()
            .get("/sessions")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test
    @Order(2)
    public void testCreateSession () {
        given()
            .contentType("application/json")
            .and()
            .body(sessionWithSpeakerId(12))
        .when()
            .post("/sessions")
        .then()
            .statusCode(201)
            .contentType("application/json")
            .body("speakerId", equalTo(12));
    }

    private Session sessionWithSpeakerId(int speakerId) {
        Session session = new Session();
        session.speakerId = speakerId;

        return session;
    }
}
