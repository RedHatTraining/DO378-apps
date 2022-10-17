package com.redhat.smartcity.weather;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


@Path("/warnings")
@RegisterRestClient
public interface WeatherService {

    @GET
    @Path("/{city}")
    List<WeatherWarning> getWarningsByCity(@PathParam("city") String city);

}
