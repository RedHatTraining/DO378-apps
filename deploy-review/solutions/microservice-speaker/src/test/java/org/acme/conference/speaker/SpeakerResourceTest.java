package org.acme.conference.speaker;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpeakerResourceTest {

    private final String[] samples = {
            "{\"name\":\"Pablo\",\"organization\":\"Red Hat\",\"talks\":[{\"title\":\"Lorem ipsum dolor sit amet\",\"duration\":15}]}",
            "{\"name\":\"Noelia\",\"organization\":\"Red Hat\",\"talks\":[{\"title\":\"Consectetur adipiscing elit\",\"duration\":20}]}",
    };

    @Test
    @Order(1)
    public void initialListOfSpeakersIsEmpty() {
        given()
        .when()
            .get("/speakers")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }

    @Test
    @Order(2)
    public void createSpeakers() {
        given()
            .body(samples[0])
            .contentType("application/json")
        .when()
            .post("/speakers")
        .then()
            .statusCode(201)
            .header("location", startsWith("http://"))
            .header("id", Integer::parseInt, greaterThanOrEqualTo(0));

        given()
            .body(samples[1])
            .contentType("application/json")
        .when()
            .post("/speakers")
        .then()
            .statusCode(201)
            .header("location", startsWith("http://"))
            .header("id", Integer::parseInt, greaterThanOrEqualTo(0));
    }

    @Test
    @Order(3)
    public void sortingSpeakersByDefault() {
        given()
        .when()
            .get("/speakers")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(2),
                    "[0].name", is("Pablo"),
                    "[1].name", is("Noelia")
            );
    }

    @Test
    @Order(3)
    public void sortingSpeakersById() {
        given()
        .when()
            .get("/speakers?sortBy=id")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(2),
                    "[0].name", is("Pablo"),
                    "[1].name", is("Noelia")
            );
    }

    @Test
    @Order(3)
    public void sortingSpeakersByName() {
        given()
        .when()
            .get("/speakers?sortBy=name")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(2),
                    "[0].name", is("Noelia"),
                    "[1].name", is("Pablo")
            );
    }

    @Test
    @Order(3)
    public void sortingSpeakersByUnknownField() {
        given()
        .when()
            .get("/speakers?sortBy=unknown")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(2),
                    "[0].name", is("Pablo"),
                    "[1].name", is("Noelia")
            );
    }

    @Test
    @Order(3)
    public void pageSizeLimitsTheResultsCollection() {
        given()
        .when()
            .get("/speakers?pageSize=1")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(1),
                    "[0].name", is("Pablo")
            );
    }

    @Test
    @Order(3)
    public void pageIndexLimitsTheResultsCollection() {
        given()
        .when()
            .get("/speakers?pageSize=1&pageIndex=1")
        .then()
            .statusCode(200)
            .body(
                    "$.size()", is(1),
                    "[0].name", is("Noelia")
            );
    }

    @Test
    @Order(4)
    public void deleteNonExistingSpeakerReturns404() {
        given()
        .when()
            .delete("/speakers/123456")
        .then()
            .statusCode(404);
    }

    @Test
    @Order(4)
    public void deleteExistingSpeakerReturns204AndRemovesItFromPersistenceLayer() {
        given()
        .when()
            .delete("/speakers/1")
       .then()
           .statusCode(204);

       given()
       .when()
           .get("/speakers")
       .then()
           .statusCode(200)
           .body(
                    "$.size()", is(1),
                    "[0].name", is("Noelia")
           );
    }
}
