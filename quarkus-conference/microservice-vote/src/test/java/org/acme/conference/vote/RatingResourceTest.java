package org.acme.conference.vote;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import javax.inject.Inject;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongo.MongoDatabaseTestResource;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(MongoDatabaseTestResource.class)
public class RatingResourceTest {

    private static final String RATING_ATTENDEE = "ATTENDEE-ID";
    private static final String RATING_SESSION = "RATING-SESSION";
    private static final int RATING_RATING = 5;

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
    public void testRateSession () {
        attendeeForge.create(RATING_ATTENDEE, "ATTENDEE-NAME");

        given().when()
                .body("{\"attendeeId\":\"" + RATING_ATTENDEE + "\",\"rating\":" + RATING_RATING + "," + "\"session\":\""
                        + RATING_SESSION + "\"}")
                .contentType(ContentType.JSON)
                .post("/rate")
                .then()
                .statusCode(200)
                .body("id", not(emptyOrNullString()))
                .body("session", equalTo(RATING_SESSION))
                .body("attendeeId", equalTo(RATING_ATTENDEE))
                .body("rating", equalTo(RATING_RATING));
    }

    @Test
    public void testGetAllSessionRatings () {
        deleteAllSessionRatings();

        attendeeForge.create(RATING_ATTENDEE, "ATTENDEE-NAME");
        SessionRating rating = createDefault();

        given().when()
                .get("/rate")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1));
    }

    @Test
    public void testUpdateRating () {
        attendeeForge.create(RATING_ATTENDEE, "ATTENDEE-NAME");
        SessionRating rating = createDefault();

        final int RATING_NEW_RATING = 1;

        given().when()
                .body("{\"attendeeId\":\"" + RATING_ATTENDEE + "\",\"rating\":" + RATING_NEW_RATING + ","
                        + "\"session\":\"" + RATING_SESSION + "\"}")
                .contentType(ContentType.JSON)
                .put("/rate/" + rating.getRatingId())
                .then()
                .statusCode(200)
                .body("ratingId", equalTo(rating.getRatingId()))
                .body("session", equalTo(RATING_SESSION))
                .body("attendeeId", equalTo(RATING_ATTENDEE))
                .body("rating", equalTo(RATING_NEW_RATING));
    }

    @Test
    public void testGetRating () {
        attendeeForge.create(RATING_ATTENDEE, "ATTENDEE-NAME");
        SessionRating rating = createDefault();

        given().when()
                .get("/rate/" + rating.getRatingId())
                .then()
                .statusCode(200)
                .body("ratingId", equalTo(rating.getRatingId()))
                .body("session", equalTo(RATING_SESSION))
                .body("attendeeId", equalTo(RATING_ATTENDEE))
                .body("rating", equalTo(RATING_RATING));
    }

    @Test
    public void testDeleteRating () {
        attendeeForge.create(RATING_ATTENDEE, "ATTENDEE-NAME");
        SessionRating rating = createDefault();

        given().when()
                .delete("/rate/" + rating.getRatingId())
                .then()
                .statusCode(204);
    }

    private SessionRating createDefault () {
        return given().when()
                .body("{\"attendeeId\":\"" + RATING_ATTENDEE + "\",\"rating\":" + RATING_RATING + "," + "\"session\":\""
                        + RATING_SESSION + "\"}")
                .contentType(ContentType.JSON)
                .post("/rate")
                .thenReturn()
                .as(SessionRating.class);
    }

    private void deleteAllSessionRatings () {
        mongoClient.getDatabase("votes")
                .getCollection("SessionRating")
                .deleteMany(new Document());
    }

}
