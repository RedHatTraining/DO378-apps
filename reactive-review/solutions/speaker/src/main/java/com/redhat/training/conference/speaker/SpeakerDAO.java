package com.redhat.training.conference.speaker;

import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@SuppressWarnings("unchecked")
public class SpeakerDAO {

    private SpeakerIdGenerator generator = new SpeakerIdGenerator();

    private static class SpeakerIdGenerator {

        public String generate() {
            return UUID.randomUUID().toString();
        }
    }

    public Uni<Speaker> create (Speaker speaker) {
        speaker.uuid=generator.generate();
        Panache.withTransaction(speaker::persist);
        return (Uni<Speaker>) speaker;
    }

    public Uni<Speaker> getByUuid(String uuid) {
        return Speaker.findById(uuid);
    }

    public Multi<Speaker> findAll(String sort) {
        return Speaker.findAll().stream();
    }

    public Uni<Void> delete (Speaker speaker) {
        return speaker.delete();
    }

    public Uni<Speaker> update (Speaker updated) {
        Panache.withTransaction(updated::persist);
        return (Uni<Speaker>) updated;
    }

}
