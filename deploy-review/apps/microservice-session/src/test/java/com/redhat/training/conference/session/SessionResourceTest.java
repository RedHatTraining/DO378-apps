package com.redhat.training.conference.session;

import com.redhat.training.conference.speaker.Speaker;
import com.redhat.training.conference.speaker.SpeakerServiceClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SessionResourceTest {

    private final String sample = "{\"sessionTitle\":\"Deploying at the Edge\",\"speakerId\": 1}";

    @RestClient
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
    public void creatingASessionReturns201WithHeaders() {
        given()
            .body(sample)
            .contentType("application/json")
        .when()
            .post("/sessions")
        .then()
            .statusCode(201)
            .header("location", startsWith("http://"))
            .header("id", notNullValue());
    }

    @Test
    @Order(3)
    public void gettingAllSessionsIncludesSpeakerInformation() {
        Mockito.when(
                speakerService.getSpeaker(Mockito.anyLong())
        ).thenReturn(new Speaker(1, "Speaker Name", "Organization Name"));

        given()
        .when()
            .get("/sessions")
        .then()
            .statusCode(200)
            .body("speaker", notNullValue());
    }

    @Test
    @Order(3)
    public void gettingASessionReturnsASpeaker() {
        Mockito.when(
                speakerService.getSpeaker(Mockito.anyLong())
        ).thenReturn(new Speaker(1, "Speaker Name", "Organization Name"));

        given()
        .when()
            .get("/sessions/1")
        .then()
            .statusCode(200)
            .body("speaker", notNullValue());
    }
}
