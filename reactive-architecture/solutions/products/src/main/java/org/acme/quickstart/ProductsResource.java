package org.acme.quickstart;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.impl.VertxThread;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @Inject
    @RestClient
    PricesService service;

    @GET
    @Path("/{productId}")
    public Uni<Product> getProductPriceHistory(@PathParam("productId") final Long productId) {
        logInfo();

        // return new Person("yo");
        return service.get(productId);
    }

    @GET
    @Blocking
    @Path("/blocks")
    public Uni<Product> bloks() {
        logInfo();

        try {
            Thread.sleep(10000);
        } catch( InterruptedException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // return new Person("yo");
        return Uni.createFrom().item(new Product("asdf"));
    }

    private void logInfo() {
        ProcessHandle processHandle = ProcessHandle.current();

        var pid = processHandle.pid();
        // var command = processHandle.info().commandLine().get();
        var threadName = VertxThread.currentThread().getName();

        Log.info("PID " + pid +  " . Thread: " + threadName);
    }
}