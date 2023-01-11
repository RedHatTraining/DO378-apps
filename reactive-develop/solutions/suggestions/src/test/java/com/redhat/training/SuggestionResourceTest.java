package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(SuggestionResource.class)
public class SuggestionResourceTest {
    @BeforeEach
    protected void cleanup() {
        given().delete();
    }

    @Test
    public void testCreateEndpoint() {
        Suggestion returnedSuggestion = createSuggestion( 1L, 103L );

        assertThat( returnedSuggestion.id ).isNotNull();
    }

    @Test
    public void testGetEndpoint() {
        Suggestion inserted = createSuggestion( 2L, 104L );

        Suggestion retrieved = given()
        .when()
            .get( inserted.id.toString() )
        .then()
            .statusCode( HttpStatus.SC_OK )
            .extract()
                .as( Suggestion.class );
        
        assertThat(retrieved.clientId).isEqualTo( 2L );
    }

    @Test
    public void testListEndpoint() {
        createSuggestion( 3L, 105L );
        createSuggestion( 4L, 106L );
        createSuggestion( 5L, 107L );

        List<Suggestion> suggestions = given()
        .when()
            .get()
        .then()
            .statusCode( HttpStatus.SC_OK )
            .extract()
            .body().jsonPath().getList( ".", Suggestion.class );

        assertThat( suggestions ).size().isEqualTo( 3 );
    }

    private Suggestion createSuggestion( Long clientId, Long itemId ) {
        Suggestion newSuggestion = new Suggestion( clientId, itemId );

        return given().body( newSuggestion )
            .when()
                .contentType( ContentType.JSON )
                .post()
            .then()
                .statusCode( HttpStatus.SC_OK )
                .extract()
                .as( Suggestion.class );
    }
}
