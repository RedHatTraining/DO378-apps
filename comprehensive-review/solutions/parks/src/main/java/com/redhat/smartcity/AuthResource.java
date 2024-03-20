package com.redhat.smartcity;


import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;


class UserCredentials {
	public String username;
	public String password;
}

class UserResponse {
    public String username;

    UserResponse(String username) {
        this.username = username;
    }
}


@Path( "/auth" )
@RequestScoped
public class AuthResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UserService userService;

    @Inject
    JwtGenerator jwtService;

    @POST
    @Path("/login")
    public Response login(UserCredentials credentials) {
        if (userService.authenticate(credentials.username, credentials.password)) {
            var token = jwtService.generateForUser(credentials.username);
            return Response.ok(token).build();
        } else {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }


    @GET
    @Path( "/user" )
    @RolesAllowed( { "Admin", "User" } )
    public UserResponse user(@Context SecurityContext context) {
        if (!context.getUserPrincipal().getName().equals(jwt.getName())) {
            throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
        }

        String username = context.getUserPrincipal().getName();
        return new UserResponse(username);
    }
}
