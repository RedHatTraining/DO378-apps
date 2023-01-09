package com.redhat.training.resource;

import com.redhat.training.event.SpeakerWasCreated;
import com.redhat.training.model.Speaker;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/speakers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpeakerResource {

    @Channel("new-speakers-out")
    Emitter<SpeakerWasCreated> emitter;

/*    public Message<Double> notifyRatioOfIncidents() {
            Uni<Long> high = Speaker.count("status", "HIGH");
            Uni<Long> total = Speaker.count();


        return Message.of(
                high.await().indefinitely().doubleValue()
                        /
                        total.await().indefinitely().doubleValue()
        );
    }*/

    @POST
    public Uni<Response> create(Speaker newSpeaker) {
        return Panache
                .<Speaker>withTransaction(newSpeaker::persist)
                .onItem()
                .transform(
                        inserted -> {
                            emitter.send(
                                    new SpeakerWasCreated(
                                            inserted.id,
                                            newSpeaker.fullName,
                                            newSpeaker.affiliation,
                                            newSpeaker.email
                                    )
                            );

                            return Response.created(
                                    URI.create("/speakers/" + inserted.id)
                            ).build();
                        }
                );
    }

    @GET
    @Path("/{id}")
    public Uni<Speaker> get(Long id) {
        return Speaker.findById(id);
    }

    @GET
    public Multi<Speaker> listAll () {
        return Speaker.streamAll();
    }
}
