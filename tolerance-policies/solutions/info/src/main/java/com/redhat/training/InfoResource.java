package com.redhat.training;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ServiceUnavailableException;

import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import com.redhat.training.services.CpuStats;
import com.redhat.training.services.Info;
import com.redhat.training.services.InfoService;
import com.redhat.training.services.LoadPredictionService;
import com.redhat.training.services.CpuStatsService;
import com.redhat.training.services.WeatherService;

@Path( "/info" )
@Produces( MediaType.APPLICATION_JSON )
public class InfoResource {

    @Inject
    InfoService infoService;

    @Inject
    WeatherService weatherService;

    @Inject
    CpuStatsService statsService;

    @Inject
    LoadPredictionService predictionService;

    @GET
    public Info hello() {
        return infoService.getInfo();
    }

    @GET
    @Path( "/weather" )
    public String conditions() {
        return weatherService.getConditions();
    }

    @GET
    @Path( "/stats" )
    public CpuStats stats() {
        return statsService.getCpuStats();
    }

    @GET
    @Path( "/predict" )
    public Double predict() {
        try {
            return predictionService.predictSystemLoad();
        } catch( CircuitBreakerOpenException e ) {
            var response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Prediction service is not available at the moment").build();
            throw new ServiceUnavailableException( response );
        }
    }
}