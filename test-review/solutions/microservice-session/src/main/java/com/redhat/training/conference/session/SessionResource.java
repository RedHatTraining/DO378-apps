package com.redhat.training.conference.session;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.enterprise.context.ApplicationScoped;

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
