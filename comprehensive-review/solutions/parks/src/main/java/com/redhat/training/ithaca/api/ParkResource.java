package com.redhat.training.ithaca.api;

import java.util.List;

import com.redhat.training.ithaca.entities.Park;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;

import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

@Path( "/parks" )
@RequestScoped
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
// @RequestScoped
public class ParkResource {


    @GET
    @Operation( summary = "List parks", description = "List all the parks registered in the system" )
    @Produces( MediaType.APPLICATION_JSON )
    public List<Park> list() {
        return Park.listAll();
    }

    @Transactional
    @PUT
    @Path( "/" )
    @RolesAllowed( { "Admin" } )
    @Operation( summary = "Update an existing park" )
    @Bulkhead( 1 )
    public void update( Park receivedPark ) {
        Park park = Park.findById(receivedPark.id);

        if (park == null) {
            throw new NotFoundException();
        }

        park.city = receivedPark.city;
        park.name = receivedPark.name;
        park.size = receivedPark.size;
        park.status = receivedPark.status;

        park.persist();
    }
}
