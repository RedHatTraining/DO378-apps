package com.redhat.training.conference.session;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import javax.inject.Inject;
import com.redhat.training.conference.speaker.SpeakerService;

@QuarkusTest
public class SessionResourceTest {

    @RestClient
    @Inject
    @InjectMock
    SpeakerService speakerService;

    @Test
    public void testCreateSession () {

        given()
            .contentType("application/json")
            .and()
            .body(sessionWithSpeakerId(12))
        .when()
            .post("/sessions")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("speakerId", equalTo(12));
    }

    private Session sessionWithSpeakerId(int speakerId) {
        Session session = new Session();
        session.speakerId = speakerId;
        return session;
    }

}
