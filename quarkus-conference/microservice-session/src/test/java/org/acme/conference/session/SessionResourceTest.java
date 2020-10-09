package org.acme.conference.session;

import static io.restassured.RestAssured.given;
import static org.acme.conference.session.SessionFakeFactory.DEFAULT_ID;
import static org.acme.conference.session.SessionFakeFactory.composeSession;
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
    public void testGetAllSessionsEndpoint () {
        given().when()
                .get("/sessions")
                .then()
                .statusCode(200)
                .body(startsWith("["), endsWith("]"));
    }

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

    @Test
    public void testRetrieveSession () {
        given().when()
                .get("/sessions/" + SESSION_ID)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(SESSION_ID));
    }

    @Test
    public void testUpdateSession () {
        final int ANOTHER_SCHEDULE = 3;
        Session session = new Session();
        session.id=SESSION_ID;
        session.schedule=ANOTHER_SCHEDULE;
        given().when()
                .body(session)
                .contentType("application/json")
                .put("/sessions/" + SESSION_ID)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(SESSION_ID))
                .body("schedule", equalTo(ANOTHER_SCHEDULE));
    }

    @Test
    public void testDeleteSession () {
        given().when()
                .delete("/sessions/" + OTHER_SESSION_ID)
                .then()
                .statusCode(204);
    }
}
