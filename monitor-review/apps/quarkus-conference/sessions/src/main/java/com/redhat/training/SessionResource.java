package com.redhat.training;

import java.util.Collection;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.micrometer.core.annotation.Counted;

@Path( "sessions" )
@ApplicationScoped
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class SessionResource {

    @Inject
    SessionStore sessionStore;

    @GET
    public Collection<Session> getAllSessions() throws Exception {
        return sessionStore.findAll();
    }

    @GET
    @Path( "/{sessionId}" )
    public Response getSession( @PathParam( "sessionId" ) final String sessionId ) {
        final Optional<Session> result = sessionStore.findById( sessionId );

        return result.map( s -> Response.ok( s ).build() ).orElseThrow( NotFoundException::new );
    }

}
