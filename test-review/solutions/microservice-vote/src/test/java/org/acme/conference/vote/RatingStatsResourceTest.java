package org.acme.conference.vote;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongo.MongoDatabaseTestResource;
import io.quarkus.test.junit.mockito.InjectMock;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;

/**
 * Tests for the rating results and statistics resource.
 *
 */
@QuarkusTest
@QuarkusTestResource(MongoDatabaseTestResource.class)
public class RatingStatsResourceTest {

    @InjectMock SessionRatingDAO sessionRatingDAO;

    private static final String RATINGSTATS_SESSION = "RASTAS-SESSION";
    private static final String RATINGSTATS_ATTENDEE = "RATINGSTATS_ATTENDEE";
     
    @BeforeEach
    public void setUp () {
        List<SessionRating> threeConsecutiveRatings = IntStream.range(3, 6)
            .mapToObj(i->{ 
                SessionRating sr = new SessionRating(); 
                sr.setRating(i); 
                sr.setAttendeeId(RATINGSTATS_ATTENDEE);
                return sr;})
            .collect(Collectors.toList());

        Mockito.when(sessionRatingDAO.getRatingsBySession(eq(RATINGSTATS_SESSION)))
          .thenReturn(threeConsecutiveRatings);
    }


    @Test
    public void testRatingsBySession () {

        given().when()
                .get("/ratingsBySession?sessionId=" + RATINGSTATS_SESSION)
                .then()
                .statusCode(200)
                .body("size()", equalTo(3));

    }

    @Test
    public void testAverageRatingBySession () {

        given().when()
                .get("/averageRatingBySession?sessionId=" + RATINGSTATS_SESSION)
                .then()
                .statusCode(200)
                .body(equalTo("4.0"));

    }

}

