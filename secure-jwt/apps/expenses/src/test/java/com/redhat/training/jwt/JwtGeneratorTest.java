package com.redhat.training.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.inject.Inject;

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
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertTrue( jwt.getGroups().contains( "USER" ), "JWT groups for regular user do not contain USER" );
    }

    @Test
    public void userJwtContainsSubjectClaim() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertEquals( "testUser", jwt.getClaim( "sub" ), "JWT 'sub' claim not set as expected" );
    }

    @Test
    public void userJwtContainsUpnClaim() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertEquals( "testUser@example.com", jwt.getClaim( "upn" ), "JWT 'upn' claim not set as expected" );
    }

    @Test
    public void userJwtContainsIssuerClaim() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertEquals( "https://example.com/redhattraining", jwt.getClaim( "iss" ), "JWT 'iss' claim not set as expected" );
    }

    @Test
    public void userJwtContainsLocaleClaim() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertNotNull( jwt.getClaim( "locale" ), "JWT 'locale' claim not set as expected" );
    }

    @Test
    public void userJwtContainsAudienceClaim() throws ParseException {
        var token = JwtGenerator.generateJwtForRegularUser( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertNotNull( jwt.getClaim( "aud" ), "JWT 'aud' claim not set as expected" );
    }

    @Test
    public void adminJwtBelongsToUserGroup() throws ParseException {
        var token = JwtGenerator.generateJwtForAdmin( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertTrue( jwt.getGroups().contains( "USER" ), "JWT groups for admin do not contain USER" );
    }

    @Test
    public void adminJwtBelongsToAdminGroup() throws ParseException {
        var token = JwtGenerator.generateJwtForAdmin( "testUser" );

        JsonWebToken jwt = jwtParser.parse( token );

        assertTrue( jwt.getGroups().contains( "ADMIN" ), "JWT groups for admin do not contain ADMIN" );
    }
}
