package org.acme.conference.schedule;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
public class ScheduleResourceTest {

    private static final int GIVEN_ID = 101;
    private static final int GIVEN_VENUE_ID = 101;

    @Test
    public void testRetrieve() {
        given()
            .when()
                .get("/schedule/" + GIVEN_ID)
                .then()
                .statusCode(200)
                .body("id", equalTo(GIVEN_ID))
                .body("venueId", equalTo(GIVEN_VENUE_ID));
    }

    @Test 
    public void testAdd() {
        given()
            .when()
                .body("{\"venueId\":1010,\"date\":\"2020-03-20\"}")
                .contentType(ContentType.JSON)
                .post("/schedule")
                .then()
                .statusCode(201)
                .header("Location", not(emptyOrNullString()))
                .body("venueId", equalTo(1010));
    }

    @Test
    public void testAllSchedules() {
        Long count = Schedule.count();

        List<Schedule> schedules = given()
            .when()
            .get("/schedule/all")
            .thenReturn().<List<Schedule>>as(List.class);
        assertThat(schedules, hasSize(count.intValue()));
    }

    @Test
    public void testDelete () {
        given().when()
                .delete("/schedule/1")
                .then()
                .statusCode(204);
    }

    @Test
    public void testRetrieveByVenue() {
        Long count = Schedule.count("venueId", 101);

        List<Schedule> scheds = given().when()
            .get("/schedule/venue/101")
            .thenReturn().<List<Schedule>>as(List.class);

        assertThat(scheds, hasSize(count.intValue()));
    }

}
