package com.redhat.training;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

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
    public Uni<ProductPriceHistory> getProductPriceHistory( @PathParam( "productId" ) final Long productId ) {
        return pricesService.getProductPriceHistory( productId );
    }

    @GET
    @Blocking
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