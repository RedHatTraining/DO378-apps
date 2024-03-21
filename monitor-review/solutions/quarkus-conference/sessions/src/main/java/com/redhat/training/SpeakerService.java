package com.redhat.training;

import java.util.Collection;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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
