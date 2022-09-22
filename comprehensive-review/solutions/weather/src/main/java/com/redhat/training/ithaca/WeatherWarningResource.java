package com.redhat.training.ithaca;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.quarkus.logging.Log;

@Path( "/warning" )
public class WeatherWarningResource {

    @Channel( "weather-warnings" )
    Emitter<WeatherWarning> emitter;


    @GET
    @Path( "/simulate" )
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
}