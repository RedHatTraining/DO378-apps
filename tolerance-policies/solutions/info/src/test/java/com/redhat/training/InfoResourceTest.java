package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InfoResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/info")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}