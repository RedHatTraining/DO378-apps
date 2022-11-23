package com.redhat.training;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ServiceUnavailableException;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import com.redhat.training.cpu.CpuStats;
import com.redhat.training.cpu.CpuStatsService;
import com.redhat.training.status.StatusService;
import com.redhat.training.cpu.CpuPredictionService;
import com.redhat.training.sysinfo.Info;
import com.redhat.training.sysinfo.InfoService;


@Path( "/" )
@Produces( MediaType.APPLICATION_JSON )
public class MonitorResource {

    @Inject
    InfoService infoService;

    @Inject
    StatusService statusService;

    @Inject
    CpuStatsService cpuStatsService;

    @Inject
    CpuPredictionService cpuPredictionService;

    @GET
    @Path( "/hello" )
    public String hello() {
        return "Cloud instance monitoring app";
    }

    @GET
    @Path( "/info" )
    public Info getSystemInfo() {
        return infoService.getInfo();
    }

    @GET
    @Path( "/status" )
    public String getWeatherConditions() {
        return statusService.getStatus();
    }

    @GET
    @Path( "/cpu/stats" )
    public CpuStats getCpuStats() {
        return cpuStatsService.getCpuStats();
    }

    @GET
    @Path( "/cpu/predict" )
    public Double predictCpuLoad() {
        try {
            return cpuPredictionService.predictSystemLoad();
        } catch( CircuitBreakerOpenException e ) {
            var response = Response
                    .status( Response.Status.SERVICE_UNAVAILABLE )
                    .entity( "Prediction service is not available at the moment" )
                    .build();
            throw new ServiceUnavailableException( response );
        }
    }
}