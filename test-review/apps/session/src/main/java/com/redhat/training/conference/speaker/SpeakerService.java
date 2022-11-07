package com.redhat.training.conference.speaker;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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