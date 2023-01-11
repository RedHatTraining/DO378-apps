package com.redhat.training.jwt;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class JwtGeneratorTest {

    @Inject
    JWTParser jwtParser;

    @Test
    public void userJwtBelongsToUserGroup() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser("testUser");

        JsonWebToken jwt = jwtParser.parse(token);

        assertTrue(jwt.getGroups().contains("USER"), "JWT groups for regular user do not contain USER");
    }

    @Test
    public void adminJwtBelongsToUserGroup() throws ParseException {
        var token = JwtGenerator.generateJwtForAdmin("testUser");

        JsonWebToken jwt = jwtParser.parse(token);

        assertTrue(jwt.getGroups().contains("USER"), "JWT groups for admin do not contain USER");
    }

    @Test
    public void adminJwtBelongsToAdminGroup() throws ParseException {
        var token = JwtGenerator.generateJwtForAdmin("testUser");

        JsonWebToken jwt = jwtParser.parse(token);

        assertTrue(jwt.getGroups().contains("ADMIN"), "JWT groups for admin do not contain ADMIN");
    }
}
