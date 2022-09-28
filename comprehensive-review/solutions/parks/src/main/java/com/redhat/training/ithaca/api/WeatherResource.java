package com.redhat.training.ithaca.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.redhat.training.ithaca.entities.Park;
import com.redhat.training.ithaca.services.ParkGuard;

@Path("/weather")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class WeatherResource {

    @Inject
    ParkGuard parkGuard;

    @GET
    @Path( "{id}" )
    @Operation( summary = "Get the Park status corresponding to the UUID" )
    @Produces( MediaType.APPLICATION_JSON )
    public String getParkStatusByUuid(@PathParam( "id" ) String id) {

        Park p = Park.findById(id);

        String status = parkGuard.checkWeather(p);
        return status;
    }
}