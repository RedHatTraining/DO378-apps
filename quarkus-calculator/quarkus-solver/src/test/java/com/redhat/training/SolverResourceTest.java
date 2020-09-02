package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SolverResourceTest {

    @Test
    public void testSolveEndpoint() {
        given()
          .when().get("/solver/3")
          .then()
             .statusCode(200)
             .body(is("3.0"));
    }

}