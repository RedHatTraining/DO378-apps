package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SessionResourceTest {

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
}
