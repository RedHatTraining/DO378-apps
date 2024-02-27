package com.redhat.training.jwt;

import static com.redhat.training.jwt.JwtGenerator.generateJwtForAdmin;
import static com.redhat.training.jwt.JwtGenerator.generateJwtForRegularUser;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path( "/jwt" )
@ApplicationScoped
public class JwtResource {

    @GET
    @Path( "/{username}" )
    public String getJwt( @PathParam( "username" ) String username ) {

        if ( username.equalsIgnoreCase( "admin" ) ) {
            return generateJwtForAdmin( username );
        }

        return generateJwtForRegularUser( username );
    }

}