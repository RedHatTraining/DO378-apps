package com.redhat.training.conference.session;

import java.util.Collection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path( "/sessions" )
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SessionResource {

    @Inject
    SessionStoreService sessionStoreService;

    @GET
    @Operation(summary = "Retrieves all the sessions")
    @APIResponse(responseCode = "200")
    public Collection<SessionWithSpeaker> getAllSessions() throws Exception {
        return sessionStoreService.getAll();
    }

    @GET
    @Operation(summary = "Retrieves the session by ID")
    @Path("/{id}")
    public SessionWithSpeaker getSession(@PathParam( "id" ) final Long id) {
        return sessionStoreService.getById(id);
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
    public Response createSession(final Session session, @Context UriInfo uriInfo) {
        return sessionStoreService.save(session, uriInfo);
    }
}
