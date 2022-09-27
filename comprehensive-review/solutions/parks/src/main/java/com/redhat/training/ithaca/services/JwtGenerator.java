package com.redhat.training.ithaca.services;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@ApplicationScoped
public class JwtGenerator {

    @Inject
    JsonWebToken jwt;

    public String generateForUser(String username) {
        return Jwt.issuer( "https://example.com/issuer" )
            .upn(username)
            .groups( new HashSet<>( Arrays.asList( "User", "Admin" ) ) )
            .claim( Claims.birthdate.name(), "2000-01-01" )
            .sign();
    }

}
