package org.acme.conference.speaker;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpeakerService {

    public List<Speaker> listAll() {
        return Speaker.listAll();
    }

    public Optional<Speaker> findByUuid(UUID uuid) {
        return Speaker.findByUuid(uuid);
    }

    public List<Speaker> search(String query) {
        return Speaker.search(query);
    }

    public Speaker create(Speaker speaker) {
        speaker.persist();
        return speaker;
    }

    public void deleteByUuid(UUID uuid) {
        Speaker.deleteByUuid(uuid);
    }
}