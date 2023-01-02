package com.redhat.training;

import com.redhat.training.service.StateService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/crash")
@ApplicationScoped
public class CrashResource {
    @Inject
    StateService applicationState;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String setCrash() {
        applicationState.down();

        return "Service not alive\n";
    }

    public boolean isAlive() {
        return applicationState.isAlive();
    }
}
