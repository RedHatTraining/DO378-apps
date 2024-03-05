package com.redhat.training.conference.speaker;

import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path( "/speaker" )
@RegisterRestClient
public interface SpeakerService {
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public List<Speaker> listAll();

    @GET
    @Path( "/{id}" )
    public Speaker getById( @PathParam( "id" ) int id );
}
