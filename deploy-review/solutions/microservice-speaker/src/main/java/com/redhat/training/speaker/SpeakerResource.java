package com.redhat.training.speaker;

import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("/speakers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @GET
    @Operation(summary = "Retrieves the list of speakers")
    @APIResponse(responseCode = "200")
    public List<Speaker> getSpeakers(
            @DefaultValue("id") @QueryParam("sortBy") String sortBy,
            @DefaultValue("0") @QueryParam("pageIndex") int pageIndex,
            @DefaultValue("25") @QueryParam("pageSize") int pageSize
    ) {
        return Speaker
                .findAll(Sort.by(filterSortBy(sortBy)))
                .page(pageIndex, pageSize)
                .list();
    }

    @POST
    @Transactional
    @Operation(summary = "Adds a speaker")
    @APIResponse(
            responseCode = "201",
            headers = {
                    @Header(
                            name = "id",
                            description = "ID of the created entity",
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Header(
                            name = "location",
                            description = "URI of the created entity",
                            schema = @Schema(implementation = String.class)
                    ),
            },
            description = "Entity successfully created"
    )
    public Response createSpeaker(Speaker newSpeaker, @Context UriInfo uriInfo) {
        newSpeaker.persist();

        return Response.created(generateUriForSpeaker(newSpeaker, uriInfo))
            .header("id", newSpeaker.id)
            .build();
    }

    @GET
    @Path("/{id}")
    @Transactional
    public Speaker getSpeaker(@PathParam("id") Long id) {
        Speaker speaker = Speaker.findById(id);

        if (speaker == null) {
            throw new NotFoundException();
        }

        return speaker;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteSpeaker(@PathParam("id") Long id) {
        Speaker speaker = Speaker.findById(id);

        if (speaker == null) {
            throw new NotFoundException();
        }

        speaker.delete();

        return Response.noContent().build();
    }

    private URI generateUriForSpeaker(Speaker speaker, UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder().path("/{id}").build(speaker.id);
    }

    private String filterSortBy(String sortBy) {
        if (!sortBy.equals("id") && !sortBy.equals("name")){
            return "id";
        }

        return sortBy;
    }
}
