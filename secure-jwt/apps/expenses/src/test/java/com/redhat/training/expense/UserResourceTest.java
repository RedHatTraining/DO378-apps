package com.redhat.training.expense;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.is;

import java.net.URL;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UserResourceTest {

    @TestHTTPEndpoint(UserResource.class)
    @TestHTTPResource("expenses")
    URL userExpensesEndpoint;

    @Test
    public void guestsCannotListExpenses() {
        given()
        .when()
            .get(userExpensesEndpoint)
        .then()
            .statusCode(401);
    }

    @Test
    public void usersCanListTheirExpenses() {

        // User "joel" has two expenses
        var bearerToken = "Bearer " + getJwt("joel");

        given()
            .headers("Authorization", bearerToken )
        .when()
            .get(userExpensesEndpoint)
        .then()
            .statusCode(200)
            .body("$.size()", is(2));
    }


    private String getJwt(String username) {
        return given().when()
            .get("/jwt/" + username)
        .then()
            .statusCode(200)
            .extract().body().asString();
    }
}
