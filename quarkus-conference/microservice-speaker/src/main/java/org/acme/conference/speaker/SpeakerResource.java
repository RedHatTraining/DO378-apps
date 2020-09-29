package org.acme.conference.speaker;

import java.util.Collection;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
import io.quarkus.panache.common.Sort;

@Path("/speaker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    private static final int SEARCH_MINIMUM_CHARS = 3;

    @Inject
    SpeakerDAO speakerDAO;

    @GET
    public Collection<Speaker> listAll () {
        return Speaker.listAll();
    }

    @GET
    @Path("/sorted")
    public Collection<Speaker> listAllSorted(@QueryParam("sort") String sortField) {
        return speakerDAO.findAll(sortBy(sortField));
    }

    private Sort sortBy(String sortField) {
        return Optional.ofNullable(sortField).map(Sort::by).orElse(Sort.by("nameLast"));
    }

    @GET
    @Path("/{uuid}")
    public Speaker findByUuid(@PathParam("uuid") String uuid) {
        return speakerDAO.getByUuid(uuid)
                    .orElseThrow(NotFoundException::new);
    }

    @GET
    @Path("/search")
    public Collection<Speaker> search(@QueryParam("query") String query, @QueryParam("sort") String sort) {
        String queryValid  = Optional.ofNullable(query)
            .filter(q -> q.length()>=SEARCH_MINIMUM_CHARS)
            .orElseThrow(() -> new BadRequestException("Insufficient number of chars"));

        return speakerDAO.search(queryValid, sortBy(sort));
    }

    @Transactional
    @POST
    public Speaker insert(Speaker speaker) {
        return speakerDAO.create(speaker);
    }

    @Transactional
    @PUT
    @Path("/{uuid}")
    public Speaker update(@PathParam("uuid") String uuid, Speaker speaker) {
        if (null==uuid || null==speakerDAO.getByUuid(uuid)) {
            throw new NotFoundException();
        }

        if (null==speaker || !uuid.equals(speaker.uuid)) {
            throw new BadRequestException();
        }

        speaker.uuid=uuid;
        return speakerDAO.update(speaker);
    }

    @Transactional
    @DELETE
    @Path("/{uuid}")
    public void remove (@PathParam("uuid") String uuid) {

        Speaker speaker = Optional.ofNullable(uuid)
            .flatMap(speakerDAO::getByUuid)
            .orElseThrow(NotFoundException::new);

        speakerDAO.delete(speaker);
    }

}
