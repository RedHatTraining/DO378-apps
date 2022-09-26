package com.redhat.training.ithaca;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.quarkus.logging.Log;

import com.redhat.training.ithaca.entities.WeatherWarning;
import com.redhat.training.ithaca.entities.WeatherWarningType;
import com.redhat.training.ithaca.entities.WeatherWarningLevel;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/warning")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class WeatherWarningResource {

    @Channel("weather-warnings")
    Emitter<WeatherWarning> emitter;

    @Inject
    public WeatherService weatherService;

    @GET
    @Path("/simulate")
    public WeatherWarning simulate() {
        var warning = new WeatherWarning(
                "Brussels",
                WeatherWarningType.Rain,
                WeatherWarningLevel.Orange,
                LocalDateTime.now(),
                LocalDateTime.now().plus( 1, ChronoUnit.DAYS ) );
        emitter.send( warning );

        Log.info("Emitted " + warning);

        return warning;
    }

    @GET
    @Path("/alerts")
    @Operation(
        summary = "List weather warning alerts",
        description = "List all the warnings alerts."
    )
    @Produces(MediaType.APPLICATION_JSON)
    public Set<WeatherWarning> listAll() {
        return weatherService.listAll();
    }

    @Transactional
    @POST
    @Operation(
        summary = "Add a new Warning alert",
        description = "Adding a new warning"
    )
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public WeatherWarning create(WeatherWarning warning) {
        return weatherService.create(warning);
    }

}
