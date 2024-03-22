package com.redhat.training;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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

    @POST
    @Transactional
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

    @PUT
    @Transactional
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
