package com.redhat.training.ithaca.services;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
public class JwtService {

    @Inject
    JsonWebToken jwt;

    public String createTokenForUser(String username) {
        return Jwt.issuer( "https://example.com/issuer" )
            .upn(username)
            .groups( new HashSet<>( Arrays.asList( "User", "Admin" ) ) )
            .claim( Claims.birthdate.name(), "2000-01-01" )
            .sign();
    }

    public JsonWebToken getToken() {
        return jwt;
    }
}
