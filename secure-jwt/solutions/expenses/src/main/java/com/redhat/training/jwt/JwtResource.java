package com.redhat.training.jwt;

import static com.redhat.training.jwt.JwtGenerator.generateJwtForAdmin;
import static com.redhat.training.jwt.JwtGenerator.generateJwtForRegularUser;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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