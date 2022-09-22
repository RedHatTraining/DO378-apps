package com.redhat.training.ithaca;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;

@QuarkusTest
public class ParksResourceTest {

    @Test
    public void testGetAllSessionsEndpoint () {
        given().when()
                .get("/parks")
                .then()
                .statusCode(200)
                .body(startsWith("["), endsWith("]"));
    }
}
