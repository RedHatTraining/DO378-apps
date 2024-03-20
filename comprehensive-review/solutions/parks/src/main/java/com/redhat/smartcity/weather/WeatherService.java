package com.redhat.smartcity.weather;

import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("/warnings")
@RegisterRestClient(configKey="weather-api")
public interface WeatherService {

    @GET
    @Path("/{city}")
    Uni<List<WeatherWarning>> getWarningsByCity(@PathParam("city") String city);

}
