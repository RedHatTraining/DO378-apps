package com.redhat.training;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class CorsTest {

    private final Config props = ConfigProvider.getConfig();

    private final String CORS_CONFIG_ORIGINS = props.getValue("quarkus.http.cors.origins", String.class);
    private final String CORS_ORIGINS = "localhost:8080";


    @Test
    public void testCorsConfig() {
        Assertions.assertTrue(CORS_CONFIG_ORIGINS.contains(CORS_ORIGINS),
                        "CORS origins configuration does not contain localhost:8080");
    }

    @Test
    @TestSecurity(user = "user", roles = {"read"})
    public void testCorsRequest() {
        given()
          .when()
            .header("Origin", "http://localhost:8080")
          .get("/speakers")
          .then()
            .statusCode(200)
            .header("Access-Control-Allow-Origin", containsString("localhost:8080"));
    }

}
