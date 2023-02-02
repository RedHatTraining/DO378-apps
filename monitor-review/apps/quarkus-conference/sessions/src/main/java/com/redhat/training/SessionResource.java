package com.redhat.training;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
