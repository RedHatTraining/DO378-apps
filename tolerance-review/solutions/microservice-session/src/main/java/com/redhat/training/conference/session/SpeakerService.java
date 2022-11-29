package com.redhat.training.conference.session;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/speaker")
@RegisterRestClient
public interface SpeakerService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpeakerFromService> listAll();

    @GET
    @Path( "/{id}" )
    public Speaker getById( @PathParam( "id" ) int id );
    
    @GET
    @Path("/search")
    public Collection<SpeakerFromService> search(@QueryParam("query") String query, @QueryParam("sort") String sort);
}
