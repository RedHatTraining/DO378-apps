package com.redhat.training;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.training.services.CpuStats;
import com.redhat.training.services.Info;
import com.redhat.training.services.InfoService;
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


    @GET
    public Info hello() {
        return infoService.getInfo();
    }

    @GET
    @Path("/weather")
    public String conditions() {
        return weatherService.getConditions();
    }
    @GET
    @Path("/stats")
    public CpuStats stats() {
        return statsService.getCpuStats();
    }
}