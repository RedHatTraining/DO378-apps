package com.redhat.training.conference.speaker;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path( "/speakers" )
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public interface SpeakerServiceClient {

    @GET
    public List<Speaker> getSpeakers();

    @GET
    @Path( "/{id}" )
    public Speaker getSpeaker(@PathParam( "id" ) Long id);
}