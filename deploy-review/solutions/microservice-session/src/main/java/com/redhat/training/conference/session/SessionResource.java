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

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 * SessionResource
 */
@Path( "sessions" )
@ApplicationScoped
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SessionResource {

    @Inject
    SessionStore sessionStore;

    @GET
    @Operation(summary = "Retrieves all the sessions")
    @APIResponse(responseCode = "200")
    public Collection<SessionWithSpeaker> getAllSessions() throws Exception {

        return sessionStore.getAll();
    }

    @GET
    @Operation(summary = "Retrieves the session by id")
    @Path( "/{sessionId}" )
    public SessionWithSpeaker getSession( @PathParam( "sessionId" ) final Long sessionId ) {

        return sessionStore.getById( sessionId );
    }

    @POST
    @Operation(summary = "Adds a session")
    @APIResponse(
            responseCode = "201",
            headers = {
                    @Header(
                            name = "id",
                            description = "ID of the created entity",
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Header(
                            name = "location",
                            description = "URI of the created entity",
                            schema = @Schema(implementation = String.class)
                    ),
            },
            description = "Entity successfully created"
    )
    public Session createSession( final Session session ) {

        return sessionStore.save( session );
    }
}
