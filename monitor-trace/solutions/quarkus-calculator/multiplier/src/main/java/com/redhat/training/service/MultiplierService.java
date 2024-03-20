package com.redhat.training.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/multiplier")
public interface MultiplierService {

    @GET
    @Path("/{lhs}/{rhs}")
    @Produces(MediaType.TEXT_PLAIN)
    Float multiply(@PathParam("lhs") String lhs, @PathParam("rhs") String rhs);

}
