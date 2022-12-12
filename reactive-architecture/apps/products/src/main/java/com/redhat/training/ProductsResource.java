package com.redhat.training;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;

@Path( "/products" )
@Produces( MediaType.APPLICATION_JSON )
public class ProductsResource {

    @Inject
    @RestClient
    PricesService pricesService;

    @GET
    @Path( "/{productId}/priceHistory" )
    public ProductPriceHistory getProductPriceHistory( @PathParam( "productId" ) final Long productId ) {
        return pricesService.getProductPriceHistory( productId );
    }

    @GET
    @Path( "/blocking" )
    public Uni<String> blocking() {
        try {
            Thread.sleep( 30000 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }

        return  Uni.createFrom().item("I am a blocking operation");
    }

}