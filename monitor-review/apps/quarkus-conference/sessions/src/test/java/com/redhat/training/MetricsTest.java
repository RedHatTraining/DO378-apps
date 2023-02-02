package com.redhat.training;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class MetricsTest {

    @Test
    public void testMetricExists() {
        given().get( "/sessions" );


        when().get( "/q/metrics" )
                .then()
                .statusCode( 200 )
                .body( containsString( "callsToGetSessions" ) );
    }

}
