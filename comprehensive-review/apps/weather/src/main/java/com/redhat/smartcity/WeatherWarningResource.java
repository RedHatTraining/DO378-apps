package com.redhat.smartcity;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.inject.Inject;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.logging.Log;

import com.redhat.smartcity.entities.WeatherWarning;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/warnings")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class WeatherWarningResource {

    @Channel("weather-warnings")
    Emitter<WeatherWarning> emitter;

    @Inject
    public WeatherWarningsRepository repository;

    @POST
    @Path("/simulation")
    @Operation(
        summary = "Create a new weather simulation",
        description = "Start a weather simulation that produces random alerts."
    )
    public void simulate() {
        repository.clear();

        Log.info("Starting new simulation");
        for(var warning:  WeatherSimulation.run()) {
            repository.add(warning);
            emitter.send(warning);
            Log.info("Emitted " + warning);
        }
    }

    @GET
    @Operation(
        summary = "List weather warnings",
        description = "List all the active warnings."
    )
    public List<WeatherWarning> listAll() {
        return repository.all();
    }

    @GET
    @Path("/{city}")
    @Operation(
        summary = "List weather warnings",
        description = "List all the active warnings."
    )
    public List<WeatherWarning> listByCity(@PathParam("city") String city) {
        return repository.listByCity(city);
    }
}
