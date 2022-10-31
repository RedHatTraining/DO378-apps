package com.redhat.training.conference.session;

import static com.redhat.training.conference.session.SessionFakeFactory.DEFAULT_ID;
import static com.redhat.training.conference.session.SessionFakeFactory.composeSession;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class SessionResourceTest {

    private static final String SESSION_ID = "s-1-1";
    private static final String OTHER_SESSION_ID = "s-1-2";

    @Test
    public void testCreateSession () {
        Session session = composeSession();
        given().when()
                .body(session)
                .contentType("application/json")
                .post("/sessions")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(DEFAULT_ID));
    }

    
}
