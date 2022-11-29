package com.redhat.training.conference.session;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.Mockito;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@TestMethodOrder(OrderAnnotation.class)
public class SessionResourceTest {

    @InjectMock
    SpeakerService speakerService;

    @Test
    public void testAllSessionsFallback() {
        given()
            .contentType("application/json")
            .when()
            .get("/sessions")
            .then()
            .statusCode(200)
            .contentType("application/json")
            .body("speakers.name[0]", equalTo("Emmanuel"));
    }

    @Test
    public void testSessionCircuitBreaker() {

        Mockito
            .when(
                speakerService.listAll()
        )
            .thenThrow(InternalServerErrorException.class)
            .thenThrow(InternalServerErrorException.class)
            .thenReturn(getSpeakersFromService());

        // 1st request fails with 500
        given()
                .contentType("application/json")
                .when()
                .get("/sessions/s-1-1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("speakers[0].name", equalTo("Emmanuel"));

        // 2nd request fails with 500
        given()
                .contentType("application/json")
                .when()
                .get("/sessions/s-1-1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("speakers[0].name", equalTo("Emmanuel"));

        // Circuit breaker is open; must default to fallback
        given()
                .contentType("application/json")
                .when()
                .get("/sessions/s-1-1")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("speakers[0].name", equalTo("Emmanuel"));
    }

    @Test
    public void testSessionSpeakerFallback() {

        Mockito
                .when(
                        speakerService.listAll()
                )
                .thenAnswer((invocation) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                    return getSpeakersFromService();
                });

        given()
                .contentType("application/json")
                .when()
                .get("/sessions/s-1-1/speakers")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name[0]", equalTo("Emmanuel"));
    }

    @Test
    public void testAddSpeakerToSession() {
        given()
                .contentType("application/json")
                .when()
                .put("/sessions/s-1-1/speakers/Clement")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("speakers.name[0]", equalTo("Emmanuel"))
                .body("speakers.name[1]", equalTo("Clement"));
    }


    private List<SpeakerFromService> getSpeakersFromService() {
        SpeakerFromService s = new SpeakerFromService();
        s.uuid = "s-1-1";
        s.nameFirst = "First";
        s.nameLast = "Last";
        List<SpeakerFromService> res = new ArrayList<>();
        res.add(s);
        return res;
    }
}
