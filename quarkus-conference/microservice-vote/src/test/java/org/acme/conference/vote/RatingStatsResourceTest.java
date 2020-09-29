package org.acme.conference.vote;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.util.stream.IntStream;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongo.MongoDatabaseTestResource;

/**
 * Tests for the rating results and statistics resource.
 *
 */
@QuarkusTest
@QuarkusTestResource(MongoDatabaseTestResource.class)
public class RatingStatsResourceTest {

    private static final String RATINGSTATS_SESSION = "RASTAS-SESSION";

    @Inject
    private MongoClient mongoClient;

    private AttendeeDataForgery attendeeForge;
    private SessionRatingDataForgery ratingForge;

    @BeforeEach
    public void setUp () {
        attendeeForge = new AttendeeDataForgery(mongoClient);
        ratingForge = new SessionRatingDataForgery(mongoClient);

        attendeeForge.create("ALTER-UUID", "ALTER NAME");
        ratingForge.create("VARY-SESSION", "ALTER-UUID", 5);
    }

    @AfterEach
    public void tearDown () {
        ratingForge.deleteAll();
        attendeeForge.deleteAll();
    }

    @Test
    public void testRatingsBySession () {
        IntStream.range(0, 3)
                .forEach(i -> {
                    String attendeeId = "ratingsBySession" + i;
                    attendeeForge.create(attendeeId, "ratingsBySession" + i);
                    ratingForge.create(RATINGSTATS_SESSION, attendeeId, 1);
                });

        given().when()
                .get("/ratingsBySession?sessionId=" + RATINGSTATS_SESSION)
                .then()
                .statusCode(200)
                .body("size()", equalTo(3));
    }

    @Test
    public void testAverageRatingBySession () {
        IntStream.range(0, 3)
                .forEach(i -> {
                    String attendeeId = "averageRatingBySession" + i;
                    attendeeForge.create(attendeeId, "averageRatingBySession" + i);
                    ratingForge.create(RATINGSTATS_SESSION, attendeeId, 3 + i);
                });

        given().when()
                .get("/averageRatingBySession?sessionId=" + RATINGSTATS_SESSION)
                .then()
                .statusCode(200)
                .body(equalTo("4.0"));
    }

    @Test
    public void testRatingsByAttendee () {
        String attendeeId = "ratingsByAttendee";
        attendeeForge.create(attendeeId, "ratingsByAttendeeName");
        IntStream.range(0, 3)
                .forEach(i -> {
                    ratingForge.create("ratingsByAttendeeSession" + i, attendeeId, 5);
                });

        given().when()
                .get("/ratingsByAttendee?attendeeId=" + attendeeId)
                .then()
                .statusCode(200)
                .body("size()", equalTo(3));
    }

}
