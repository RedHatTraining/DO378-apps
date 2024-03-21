package com.redhat.training.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/solver")
public interface SolverService {
    @GET
    @Path("{equation}")
    @Produces(MediaType.TEXT_PLAIN)
    Float solve(@PathParam("equation") String equation);
}
