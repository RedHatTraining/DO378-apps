package com.redhat.smartcity;

import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtGenerator {

    public String generateForUser( String username ) {
        return Jwt.issuer( "https://example.com/issuer" )
                .upn( username )
                .groups( Set.of( "User", "Admin" ) )
                .claim( Claims.birthdate.name(), "2000-01-01" )
                .sign();
    }

}
