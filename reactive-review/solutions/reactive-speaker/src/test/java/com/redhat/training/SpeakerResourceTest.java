package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;

import com.redhat.training.model.Affiliation;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SpeakerResourceTest {

    @Test
    public void listOfSpeakersReturnsEmptyListOnStartup() {
        given()
        .when()
            .get("/speakers")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test
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

    public static String generateRequestBody(Affiliation affiliation) {
        return "{" +
                "\"fullName\":\"A Name\", " +
                "\"affiliation\":\"" + affiliation + "\"," +
                "\"email\":\"email@example.com\"" +
                "}";
    }
}
