package org.acme.quickstart;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
// import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/")
@RegisterRestClient
public interface PricesService {

    @GET
    @Path("/{productId}")
    Uni<Product> get(@PathParam("productId") final Long productId);
}