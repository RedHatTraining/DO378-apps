package org.acme.conference.speaker;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;

@Path("/speaker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @Inject
    SpeakerService service;

    @GET
    public List<Speaker> listAll() {
        return service.listAll();
    }

    @GET
    @Path("/search")
    public List<Speaker> search (@QueryParam("q") String query) {
        return service.search(query);
    }

    @GET
    @Path("/{uuid}")
    public Speaker findByUuid(@PathParam("uuid") UUID uuid) {
        return service.findByUuid(uuid)
                    .orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    public Speaker create(Speaker newSpeaker) {
        service.create(newSpeaker);
        return newSpeaker;
    }

    @DELETE
    @Transactional
    @Path("/{uuid}")
    public void delete(@PathParam("uuid") UUID uuid) {
        service.deleteByUuid(uuid);
    }
}