package com.redhat.training.ithaca;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/parks")
public class ParkResource {

    @GET
    public List<Park> hello() {
        return Park.listAll();
    }
}