package com.redhat.training;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.redhat.training.service.MultiplierService;
import com.redhat.training.service.SolverService;

@Path("/multiply")
public class MultiplierResource implements MultiplierService {
    final Logger log = LoggerFactory.getLogger(MultiplierResource.class);

    @Inject
    @RestClient
    SolverService solverService;

    @GET
    @Path("/{lhs}/{rhs}")
    @Produces(MediaType.TEXT_PLAIN)
    public String multiply(@PathParam("lhs") String lhs, @PathParam("rhs") String rhs) {
        log.info("Multiplying {} by {}",lhs, rhs);
        Float lResponse = parseOrSolve(lhs);
        Float rResponse = parseOrSolve(rhs);
        return Float.valueOf(lResponse*rResponse).toString();
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