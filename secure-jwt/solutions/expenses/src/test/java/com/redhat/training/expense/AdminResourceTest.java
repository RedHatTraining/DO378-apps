package com.redhat.training.expense;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AdminResourceTest {

    @TestHTTPEndpoint(AdminResource.class)
    @TestHTTPResource("expenses")
    URL expensesEndpoint;

    @Test
    public void guestsCannotListExpenses() {
        given()
        .when()
            .get(expensesEndpoint)
        .then()
            .statusCode(401);
    }

    @Test
    public void regularUsersCannotListExpenses() {
        // User "joel" is a regular user
        var bearerToken = "Bearer " + getJwt("joel");

        given()
            .headers("Authorization", bearerToken )
        .when()
            .get(expensesEndpoint)
        .then()
            .statusCode(403);
    }

    @Test
    public void adminsCanListAllExpenses() {
        // User "admin" is an adminstrator
        var bearerToken = "Bearer " + getJwt("admin");

        given()
            .headers("Authorization", bearerToken )
        .when()
            .get(expensesEndpoint)
        .then()
            .statusCode(200)
            .body("$.size()", is(3));
    }


    private String getJwt(String username) {
        return given().when()
            .get("/jwt/" + username)
        .then()
            .statusCode(200)
            .extract().body().asString();
    }
}
