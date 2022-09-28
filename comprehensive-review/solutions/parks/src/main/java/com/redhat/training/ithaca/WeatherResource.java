package com.redhat.training.ithaca;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/weather")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class WeatherResource {

    @Inject
    ParkGuard parkGuard;

    @Inject
    ParkService parkService;

    @GET
    @Path( "{uuid}" )
    @Operation( summary = "Get the Park status corresponding to the UUID" )
    @Produces( MediaType.APPLICATION_JSON )
    public String getParkStatusByUuid(@PathParam( "uuid" ) String uuid) {

        String status = parkGuard.checkWeather(parkService.getParkByUuid(uuid));
        return status;
    }
}