package com.redhat.training.conference.session;

import java.util.Collection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * SessionResource
 */
@Path( "sessions" )
@ApplicationScoped
@Produces( MediaType.APPLICATION_JSON )
public class SessionResource {

    @Inject
    SessionStore sessionStore;

    @GET
    public Collection<SessionWithSpeaker> getAllSessions() throws Exception {
        return sessionStore.getAll();
    }

    @GET
    @Path( "/{sessionId}" )
    public SessionWithSpeaker getSession( @PathParam( "sessionId" ) final Long sessionId ) {
        return sessionStore.getById( sessionId );
    }

    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    public Session createSession( final Session session ) {
        return sessionStore.save( session );
    }
}
