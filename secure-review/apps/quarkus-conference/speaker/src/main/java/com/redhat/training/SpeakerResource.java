package com.redhat.training;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/speakers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    private SpeakerIdGenerator generator = new SpeakerIdGenerator();

    @GET
    public List<Speaker> getSpeakers() {
        return Speaker.listAll();
    }

    @GET
    @Path("/{uuid}")
    public Optional<Speaker> findByUuid(@PathParam("uuid") String uuid) {

        if (uuid == null) {
            throw new NotFoundException();
        }
        return Speaker.find("uuid", uuid).firstResultOptional();
    }

    @Transactional
    @POST
    public Speaker insert(Speaker speaker) {
        speaker.uuid=generator.generate();
        speaker.persist();
        return speaker;
    }

    private static class SpeakerIdGenerator {

        public String generate() {
            return UUID.randomUUID().toString();
        }
    }

    @Transactional
    @PUT
    @Path("/{uuid}")
    public Speaker update(@PathParam("uuid") String uuid, Speaker speaker) {
        if (null==uuid || null==Speaker.find("uuid", uuid)) {
            throw new NotFoundException();
        }

        if (null==speaker || !uuid.equals(speaker.uuid)) {
            throw new BadRequestException();
        }

        speaker.uuid=uuid;
        speaker.persist();
        return speaker;
    }
}
