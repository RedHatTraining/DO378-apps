package com.redhat.training.conference.speaker;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/speaker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @Inject
    SpeakerDAO speakerDAO;

    @POST
    public Uni<Speaker> insert(Speaker speaker) {
        return speakerDAO.create(speaker);
    }

    @GET
    @NonBlocking
    @Path("/findBy/{uuid}")
    public Uni<Speaker> findByUuid(@PathParam("uuid") String uuid) {
        return speakerDAO.getByUuid(uuid);
    }

    @GET
    @NonBlocking
    public Multi<Speaker> listAll () {
        return Speaker.streamAll();
    }

    @GET
    @Path("/sorted")
    public Multi<Speaker> listAllSorted(@QueryParam("sort") String sortField) {
        return speakerDAO.findAll(sortField);
    }

    @PUT
    @Blocking
    @Path("/update/{uuid}")
    public Uni<Speaker> update(@PathParam("uuid") String uuid, Speaker speaker) {
        if (null==uuid || null==speakerDAO.getByUuid(uuid)) {
            throw new NotFoundException();
        }

        if (null==speaker || !uuid.equals(speaker.uuid)) {
            throw new BadRequestException();
        }

        speaker.uuid=uuid;
        return speakerDAO.update(speaker);
    }

    @DELETE
    @Path("/delete/{uuid}")
    public Uni<Void> remove (@PathParam("uuid") String uuid) {

        Speaker speaker = (Speaker) Optional.ofNullable(uuid).
                          map(speakerDAO::getByUuid).
                          orElseThrow(NotFoundException::new);

        return speakerDAO.delete(speaker);
    }
}
