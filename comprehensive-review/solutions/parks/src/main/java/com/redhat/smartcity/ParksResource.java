package com.redhat.smartcity;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.inject.Inject;
import io.smallrye.mutiny.Uni;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path( "/parks" )
@Produces( MediaType.APPLICATION_JSON )
public class ParksResource {

    @Inject
    ParkGuard guard;

    @GET
    @Operation( summary = "List parks", description = "List all the parks registered in the system" )
    public List<Park> list() {
        return Park.listAll();
    }

    @PUT
    @Operation( summary = "Update park", description = "Updates a single park" )
    @RolesAllowed( { "Admin" } )
    @Transactional
    public void update( Park receivedPark ) {
        Park park = Park.findById( receivedPark.id );

        if ( park == null ) {
            throw new NotFoundException();
        }

        park.city = receivedPark.city;
        park.name = receivedPark.name;
        park.size = receivedPark.size;
        park.status = receivedPark.status;

        park.persist();
    }

    @POST
    @Path("/{id}/weathercheck")
    @Operation(
        summary = "Weather check",
        description = "Starts a new weather check. If weather warnings are active for the city, the park might be closed"
    )
    @Transactional
    public Uni<Void>  checkWeather( @PathParam("id") Long id ) {
        Park park = Park.findById( id );

        if ( park == null ) {
            throw new NotFoundException();
        }

        return guard.checkWeatherForPark(park);
    }
}
