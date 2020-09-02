package com.redhat.training;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.service.AdderService;
import com.redhat.training.service.SolverService;

@Path("/add")
public class AdderResource implements AdderService {

    final Logger log = LoggerFactory.getLogger(AdderResource.class);
    
    @Inject
    @RestClient
    SolverService solverService;

    @GET
    @Path("/{lhs}/{rhs}")
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@PathParam("lhs") String lhs, @PathParam("rhs") String rhs) {
        log.info("Adding {} to {}" ,lhs, rhs);
        Float lResponse = parseOrSolve(lhs);
        Float rResponse = parseOrSolve(rhs);
        return Float.valueOf(lResponse+rResponse).toString();
    }

    private Float parseOrSolve(String side) {
        try {
            return Float.valueOf(side);
        } catch (NumberFormatException e) {
            try {
                return Float.valueOf(solverService.solve(side));
            } catch (Exception e2) {
                throw new WebApplicationException(side, Response.status(Response.Status.BAD_REQUEST).entity("Unable to evaluate "+side+"\nReason: "+e2.getMessage()).build());
            }
        }
    }

}