package org.acme.conference.speaker;

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

}
