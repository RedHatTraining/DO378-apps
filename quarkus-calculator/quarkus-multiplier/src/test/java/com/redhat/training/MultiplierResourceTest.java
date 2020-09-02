package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MultiplierResourceTest {

    @Test
    public void testMultiplyEndpoint() {
        given()
          .when().get("/multiply/8/2")
          .then()
             .statusCode(200)
             .body(is("16.0"));
    }

}