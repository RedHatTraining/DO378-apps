package com.redhat.training.ithaca;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyArray;
import static io.restassured.RestAssured.given;

@QuarkusTest
public class ParksResourceTest {

    @Test
    public void testGetAllSessionsEndpoint () {
        given()
                .when()
                .get( "/parks" )
                .then()
                .statusCode( 200 )
                .and()
                .body( not( emptyArray() ) );
    }
}
