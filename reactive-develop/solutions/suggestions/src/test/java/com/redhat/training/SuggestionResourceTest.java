package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

@QuarkusTest
@TestHTTPEndpoint(SuggestionResource.class)
public class SuggestionResourceTest {
    @BeforeEach
    protected void cleanup() {
        given().delete();
    }

    @Test
    public void testCreateEndpoint() {
        Suggestion returnedSuggestion = createSuggestion( 1L, 103L, 200 );

        assertThat( returnedSuggestion.id ).isNotNull();
    }

    @Test
    public void testInvalidCreateParameters() {
        createSuggestion( null, 103L, 400, "clientId", "must not be null" );
        createSuggestion( 2L, null, 400, "itemId", "must not be null" );
    }

    @Test
    public void testGetEndpoint() {
        Suggestion inserted = createSuggestion( 2L, 104L, 200 );

        Suggestion retrieved = given()
        .when()
            .get( inserted.id.toString() )
        .then()
            .statusCode( 200 )
            .extract()
                .as( Suggestion.class );
        
        assertThat(retrieved.clientId).isEqualTo( 2L );
    }

    @Test
    public void testListEndpoint() {
        createSuggestion( 3L, 105L, 200 );
        createSuggestion( 4L, 106L, 200 );
        createSuggestion( 5L, 107L, 200 );

        List<Suggestion> suggestions = given()
        .when()
            .get()
        .then()
            .statusCode(200)
            .extract()
            .body().jsonPath().getList( ".", Suggestion.class );

        assertThat( suggestions ).size().isEqualTo( 3 );
    }

    private Suggestion createSuggestion( Long clientId, Long itemId, int expectedStatusCode, String... errors ) {
        Suggestion newSuggestion = new Suggestion( clientId, itemId );

        ValidatableResponse statusCode = given().body( newSuggestion )
            .when()
                .contentType( ContentType.JSON )
                .post()
            .then()
                .statusCode( expectedStatusCode );

        if( expectedStatusCode == 200 ) {
            return statusCode
                .extract()
                .as( Suggestion.class );
        } else {
            Stream.of(errors).map( e -> statusCode.body( Matchers.containsString(e) ) ).toList();
            return null;
        }
    }
}
