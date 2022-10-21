package org.acme.rest.json;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;;

@QuarkusTest
public class ExpenseResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/expenses")
          .then()
             .statusCode(200)
             .body(containsString("amount"));
    }

}