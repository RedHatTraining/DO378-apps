package com.redhat.training.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("/solver")
public interface SolverService {
    @GET
    @Path("{equation}")
    @Produces(MediaType.TEXT_PLAIN)
    Float solve(@PathParam("equation") String equation);
}
