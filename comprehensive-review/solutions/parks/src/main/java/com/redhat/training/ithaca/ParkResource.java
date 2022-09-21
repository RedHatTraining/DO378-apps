package com.redhat.training.ithaca;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.openapi.annotations.Operation;


@Path("/parks")
@RequestScoped
public class ParkResource {

    @GET

    @Operation(
        summary = "List parks",
        description = "List all the parks registered in the system"
    )
    public List<Park> hello() {
        return Park.listAll();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    @Transactional
    public void delete(@PathParam("id") Long id) {
        var park = Park.findById(id);
        if (park == null) {
            throw new NotFoundException();
        }
        park.delete();
    }
}