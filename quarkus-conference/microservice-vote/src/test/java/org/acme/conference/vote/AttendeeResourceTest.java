package org.acme.conference.vote;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import java.util.Collections;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongo.MongoDatabaseTestResource;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(value = MongoDatabaseTestResource.class)
public class AttendeeResourceTest {

    private static final String ATTENDEE_NAME = "Attendee Lastname";
    private static final String ATTENDEE_DEFAULT_NAME = "Default Name";

    @Inject
    private MongoClient mongoClient;

    private AttendeeDataForgery attendeeForge;

    @BeforeEach
    public void setUp () {
        attendeeForge = new AttendeeDataForgery(mongoClient);
    }

    @AfterEach
    public void tearDown () {
        attendeeForge.deleteAll();
    }

    @Test
    public void testCreateEndpoint () {
        given().when()
                .body("{\"name\":\"" + ATTENDEE_NAME + "\"}")
                .contentType(ContentType.JSON)
                .post("/attendee")
                .then()
                .statusCode(200)
                .body("name", equalTo(ATTENDEE_NAME))
                .body("id", not(emptyString()));
    }

    @Test
    public void testUpdateEndpoint () {

        Attendee attendee = createDefault();

        given().when()
                .body(Collections.singletonMap("name", ATTENDEE_NAME))
                .contentType(ContentType.JSON)
                .put("/attendee/" + attendee.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(attendee.getId()))
                .body("name", equalTo(ATTENDEE_NAME));
    }

    @Test
    public void testGetAllEndpoint () {
        Attendee attendee = createDefault();
        Attendee attendeeNamed = attendeeForge.createWithName("With Name");

        given().when()
                .get("/attendee")
                .then()
                .statusCode(200)
                .body("size()", equalTo(2));
    }

    @Test
    public void testGetEndpoint () {
        final String ATT_GETWITH_NAME = "GetWith Name";
        Attendee attendee = attendeeForge.createWithName(ATT_GETWITH_NAME);

        given().when()
                .get("/attendee/" + attendee.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(attendee.getId()))
                .body("name", equalTo(ATT_GETWITH_NAME));
    }

    @Test
    public void testDeleteEndpoint () {
        Attendee attendee = createDefault();

        given().when()
                .delete("/attendee/" + attendee.getId())
                .then()
                .statusCode(204);
    }

    private Attendee createDefault () {
        return given().when()
                .body("{\"name\":\"" + ATTENDEE_DEFAULT_NAME + "\"}")
                .contentType(ContentType.JSON)
                .post("/attendee")
                .thenReturn()
                .as(Attendee.class);
    }

}
