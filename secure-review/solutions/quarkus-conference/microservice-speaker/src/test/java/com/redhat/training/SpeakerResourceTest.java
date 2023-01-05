package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SpeakerResourceTest {

    @Test
    public void testListAll() {
        given()
          .when().get("/speaker")
          .then()
             .statusCode(200)
             .body("size()",is(4))
             ;
    }

    @Test
    public void deleteNonExistingSpeakerReturns404() {
        given()
        .when()
            .delete("/speakers/987654")
        .then()
            .statusCode(404);
    }

}
