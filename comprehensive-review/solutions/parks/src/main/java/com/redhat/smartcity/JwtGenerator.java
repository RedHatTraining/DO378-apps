package com.redhat.smartcity;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.jwt.Claims;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class JwtGenerator {

    public String generateForUser( String username ) {
        return Jwt.issuer( "https://example.com/issuer" )
                .upn( username )
                .groups( new HashSet<>( Arrays.asList( "User", "Admin" ) ) )
                .claim( Claims.birthdate.name(), "2000-01-01" )
                .sign();
    }

}
