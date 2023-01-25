package com.redhat.training.service;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/adder")
@RegisterRestClient
public interface AdderService {

    @GET
    @Path("/{lhs}/{rhs}")
    @Produces(MediaType.TEXT_PLAIN)
    Float add(@PathParam("lhs") String lhs, @PathParam("rhs") String rhs);
}
