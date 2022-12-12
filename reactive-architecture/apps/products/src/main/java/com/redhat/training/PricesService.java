package com.redhat.training;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path( "/" )
@RegisterRestClient
public interface PricesService {

    @GET
    @Path( "/history/{productId}" )
    ProductPriceHistory getProductPriceHistory( @PathParam( "productId" ) final Long productId );
}