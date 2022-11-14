package com.redhat.training.conference.session;

import org.junit.jupiter.api.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;
import com.redhat.training.conference.speaker.SpeakerService;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionResourceTest {

    @RestClient
    @Inject
    @InjectMock
    SpeakerService speakerService;

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
