package com.redhat.training.ithaca;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/parks")

public class ParkResource {

    @GET
    @Operation(
        summary = "List parks",
        description = "List all the parks registered in the system"
    )
    public List<Park> hello() {
        return Park.listAll();
    }
}