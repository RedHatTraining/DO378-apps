package com.redhat.training.ithaca;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.BadRequestException;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

@Path("/parks")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ParkResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public ParkService parkService;

    @GET
    @Operation(
        summary = "List parks",
        description = "List all the parks registered in the system"
    )
    @Produces(MediaType.APPLICATION_JSON)
    // @Fallback(fallbackMethod = "allSessionsFallback", applyOn = { Exception.class })
    public Set<Park> list() {
        return parkService.list();
    }

    @Transactional
    @Operation(
        summary = "Add a new Park"
    )
    @POST
    public Park create(Park park) {
        return parkService.create(park);
    }

    @Transactional
    @Operation(
        summary = "Delete an existing Park"
    )
    @DELETE
    @Path("{uuid}")
    public Set<Park> delete(@PathParam("uuid") String uuid) {
        if (!parkService.delete(uuid)) {
            throw new NotFoundException();
        }
        return parkService.list();
    }


    @Transactional
    @PUT
    @Operation(
        summary = "Update an existing park"
    )
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Bulkhead(1)
    @Path("/{uuid}")
    public void update(@PathParam("uuid") String uuid, Park park) {
        if (null==uuid || null==park.getUuid()) {
            throw new NotFoundException();
        }

        if (null==park || !uuid.equals(park.uuid)) {
            throw new BadRequestException();
        }

        park.uuid=uuid;
        parkService.update(park);
    }
}
