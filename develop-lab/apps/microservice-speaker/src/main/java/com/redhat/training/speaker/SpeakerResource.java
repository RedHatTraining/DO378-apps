package com.redhat.training.speaker;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

@Path("/speakers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    Set<Speaker> speakers = Collections.newSetFromMap(
            Collections.synchronizedMap(new LinkedHashMap<>())
    );

    @GET
    public Set<Speaker> getSpeakers() {
        return speakers;
    }

    @POST
    public Response createSpeaker(Speaker newSpeaker, @Context UriInfo uriInfo) {
        speakers.add(newSpeaker);

        return Response.created(generateUriForSpeaker(newSpeaker, uriInfo))
            .header("id", newSpeaker.ID)
            .build();
    }

    private URI generateUriForSpeaker(Speaker speaker, UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder().path("/{id}").build(speaker.ID);
    }
}
