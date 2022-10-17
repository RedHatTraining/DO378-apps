package com.redhat.expenses;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ExpenseResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/expenses")
          .then()
             .statusCode(200);
    }

}