package com.redhat.smartcity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyArray;

@QuarkusTest
public class ParksResourceTest {

    @Test
    public void testGetAllParksEndpoint () {
        given()
                .when()
                .get( "/parks" )
                .then()
                .statusCode( 200 )
                .and()
                .body( not( emptyArray() ) );
    }

}
