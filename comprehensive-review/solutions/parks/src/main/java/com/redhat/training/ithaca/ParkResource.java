package com.redhat.training.ithaca;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import io.quarkus.logging.Log;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

@Path( "/parks" )
@RequestScoped
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class ParkResource {

    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    public ParkService parkService;
    
    @GET
    @Operation( summary = "List parks", description = "List all the parks registered in the system" )
    @Produces( MediaType.APPLICATION_JSON )
    public Set<Park> list() {
        return parkService.list();
    }

    @Transactional
    @Operation( summary = "Add a new Park" )
    @POST
    public Park create( Park park ) {
        return parkService.create( park );
    }

    @Transactional
    @Operation( summary = "Delete an existing Park" )
    @DELETE
    // @RolesAllowed({ "Admin" })
    @Path( "{uuid}" )
    public Set<Park> delete( @PathParam( "uuid" ) String uuid ) {
        if ( !parkService.delete( uuid ) ) {
            throw new NotFoundException();
        }
        return parkService.list();
    }

    @Transactional
    @PUT
    @Path( "" )
    @RolesAllowed( { "Admin" } )
    @Operation( summary = "Update an existing park" )
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    @Bulkhead( 1 )
    public void update( Park park ) {
        if ( park.getUuid() == null ) {
            throw new NotFoundException();
        }

        Log.info( "New park size " + park.getSize() );

        parkService.update( park );
    }

    @GET
    @Path("{uuid}")
    @Operation( summary = "Get park by UUID" )
    @Produces( MediaType.APPLICATION_JSON )
    public Park getParkByUuid( @PathParam( "uuid" ) String Uuid ) {
        return parkService.getParkByUuid(Uuid);
    }
}
