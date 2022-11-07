package com.redhat.training.conference;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.util.List;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.common.http.TestHTTPEndpoint;

import io.restassured.http.ContentType;


public class ScheduleResourceTest {

    private static final int GIVEN_ID = 101;
    private static final int GIVEN_VENUE_ID = 101;

    @Test
    public void testRetrieve() {
        given()
            .when()
                .get("/" + GIVEN_ID)
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
                .post()
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
            .get("/all")
            .thenReturn().<List<Schedule>>as(List.class);
        assertThat(schedules, hasSize(count.intValue()));
    }

    @Test
    public void testRetrieveByVenue() {
        Long count = Schedule.count("venueId", 101);

        List<Schedule> scheds = given().when()
            .get("/venue/101")
            .thenReturn().<List<Schedule>>as(List.class);

        assertThat(scheds, hasSize(count.intValue()));
    }

}
