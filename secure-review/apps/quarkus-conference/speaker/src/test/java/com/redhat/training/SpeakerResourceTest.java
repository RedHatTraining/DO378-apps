package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
public class SpeakerResourceTest {

    @Test
    @TestSecurity(user = "user", roles = {""})
    public void testListAllUnauthorized() {
        given()
          .when()
            .get("/speakers")
          .then()
            .statusCode(403);
    }

    @Test
    @TestSecurity
    public void testListAllUnauthenticated() {
        given()
          .when()
            .get("/speakers")
          .then()
            .statusCode(401);
    }

    @Test
    @TestSecurity(user = "user", roles = {"read"})
    public void testListAll() {
        given()
          .when()
            .get("/speakers")
          .then()
            .statusCode(200)
            .body("size()", is(4));
    }

    @Test
    @TestSecurity(user = "user", roles = {"read"})
    public void testListUser() {
      given()
        .when()
          .get("/speakers/s-1-1")
        .then()
          .statusCode(200)
          .body("nameFirst", is("Emmanuel"));
    }

    @Test
    @TestSecurity(user = "user", roles = {""})
    public void testListUserUnauthorized() {
        given()
          .when()
            .get("/speakers/s-1-1")
          .then()
            .statusCode(403);
    }

    @Test
    @TestSecurity(user = "user", roles = {"read"})
    public void testInsertUserUnauthorized() {
        given()
          .body(getSpeaker(false))
          .contentType(ContentType.JSON)
          .when()
            .post("/speakers")
          .then()
            .statusCode(403);
    }

    @Test
    @TestSecurity(user = "user", roles = {"modify"})
    @TestTransaction
    public void testInsertUser() {
        given()
          .body(getSpeaker(false))
          .contentType(ContentType.JSON)
          .when()
            .post("/speakers")
          .then()
            .statusCode(200)
            .body("size()", is(5));
    }

    @Test
    @TestSecurity(user = "user", roles = {"read"})
    @TestTransaction
    public void testModifyUserUnauthorized() {
        given()
          .body(getSpeaker(true))
          .contentType(ContentType.JSON)
          .when()
            .put("/speakers/s-1-1")
          .then()
            .statusCode(403);
    }

    @Test
    @TestSecurity(user = "user", roles = {"modify"})
    public void testModifyUser() {
        given()
          .body(getSpeaker(true))
          .contentType(ContentType.JSON)
          .when()
            .put("/speakers/s-1-1")
          .then()
            .statusCode(200)
            .body("nameFirst", is("test"));
    }


    private Speaker getSpeaker(boolean uuidInUse) {
         Speaker s = new Speaker();
         s.nameLast = s.nameFirst = "test";
        if(uuidInUse)
            s.uuid = "s-1-1";
        return s;
    }
}
