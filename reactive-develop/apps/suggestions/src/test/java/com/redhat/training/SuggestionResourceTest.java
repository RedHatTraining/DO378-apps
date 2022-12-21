package com.redhat.training;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
    }
}
