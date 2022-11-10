package com.redhat.training.conference.session;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.panache.mock.PanacheMock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class SessionMockTest {

    @BeforeAll
    public static void setup() {
        PanacheMock.mock(Session.class);
    }

    @Test
    public void listOfSessionsReturnsAnEmptyList() {
        Mockito.when(Session.listAll()).thenReturn(Collections.emptyList());

        given()
        .when()
            .get("/sessions")
        .then()
            .statusCode(200)
            .body("$.size()", is(0));
    }
}
