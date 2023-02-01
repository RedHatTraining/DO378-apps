package com.redhat.training;

import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path( "/speaker" )
@RegisterRestClient
@ApplicationScoped
public interface SpeakerService {
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public List<SpeakerFromService> listAll();

    @GET
    @Path( "/{id}" )
    public Speaker getById( @PathParam( "id" ) int id );

    @GET
    @Path( "/search" )
    public Collection<SpeakerFromService> search( @QueryParam( "query" ) String query, @QueryParam( "sort" ) String sort );
}
