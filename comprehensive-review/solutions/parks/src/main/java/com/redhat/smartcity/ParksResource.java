package com.redhat.smartcity;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.inject.Inject;
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
        Park
            .<Park>findByIdOptional( receivedPark.id )
            .ifPresentOrElse(
                    park -> {
                        park.city = receivedPark.city;
                        park.name = receivedPark.name;
                        park.size = receivedPark.size;
                        park.status = receivedPark.status;

                        park.persist();
                    },
                    () -> {
                        throw new NotFoundException();
                    } );
    }

    @POST
    @Path( "/{id}/weathercheck" )
    @Operation( summary = "Weather check", description = "Starts a new weather check. If weather warnings are active for the city, the park might be closed" )
    @Transactional
    public Uni<Void> checkWeather( @PathParam( "id" ) Long id ) {
        return Park
                .<Park>findByIdOptional( id )
                .map( guard::checkWeatherForPark )
                .orElseThrow( NotFoundException::new );
    }
}
