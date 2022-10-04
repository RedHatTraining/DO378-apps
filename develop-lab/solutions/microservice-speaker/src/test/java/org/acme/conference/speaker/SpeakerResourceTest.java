package org.acme.conference.speaker;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyArray;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SpeakerResourceTest {
    
    @Test
    public void testListAll() {
        given()
          .when().get("/speaker")
          .then()
             .statusCode(200)
             .body("size()",is(8));
    }

    @Test
    public void testGetAllSessionsEndpoint () {
        given()
                .when()
                .get( "/speaker" )
                .then()
                .statusCode( 200 )
                .and()
                .body( not( emptyArray() ) );
    }
}
