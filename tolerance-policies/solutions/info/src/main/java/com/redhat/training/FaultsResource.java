package com.redhat.training;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.redhat.training.services.InfoService;


@Path("/faults")
public class FaultsResource {

    @Inject
    InfoService infoService;

    @POST
    @Path("/crash")
    public String activateCrash() {
        return "'crash' has been set to true";
    }

    @POST
    @Path("/reset")
    public String deactivateCrashes() {
        return "'crash' has been set to true";
    }
}
