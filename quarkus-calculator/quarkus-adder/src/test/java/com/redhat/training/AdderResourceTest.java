package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AdderResourceTest {

    @Test
    public void testAdderEndpoint() {
        given()
          .when().get("/add/4/7")
          .then()
             .statusCode(200)
             .body(is("11.0"));
    }

}