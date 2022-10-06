package com.redhat.smartcity;


import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

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
