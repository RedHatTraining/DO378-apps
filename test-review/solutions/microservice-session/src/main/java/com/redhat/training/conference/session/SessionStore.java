package com.redhat.training.conference.session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.redhat.training.conference.speaker.Speaker;
import com.redhat.training.conference.speaker.SpeakerFromService;
import com.redhat.training.conference.speaker.SpeakerRepository;
import com.redhat.training.conference.speaker.SpeakerService;



@ApplicationScoped
public class SessionStore {

    @Inject
    public SpeakerRepository speakerRepository;

    @ConfigProperty(name = "features.session-integration", defaultValue = "false")
    boolean sessionIntegration;

    @Inject
    @RestClient
    SpeakerService speakerService;

    public SessionStore() {
    }

    public Collection<Session> findAll() {
        // Feature toggle
        if (sessionIntegration){
            return findAllWithEnrichment();
        } else {
            return findAllWithoutEnrichment();
        }
    }

    public Collection<Session> findAllWithEnrichment(){
        List<Session> sessions = Session.findAll().list();
        final List<SpeakerFromService> allSpeakers = speakerService.listAll();
        sessions.stream().flatMap(s->s.speakers.stream()).forEach(sp->this.enrichSpeaker(allSpeakers, sp));
        return sessions;
}

    public Collection<Session> findAllWithoutEnrichment(){
        return Session.findAll().list();
    }

    @Transactional
    public Session save(Session session) {
        session.persist();
        return session;
    }

    public Optional<Session> findById(String sessionId) {
        // Feature toggle
        if (sessionIntegration) {
            return findByIdWithEnrichedSpeakers(sessionId);
        } else {
            return findByIdWithoutEnrichment(sessionId);
        }
    }

    public Optional<Session> findByIdWithoutEnrichment(String sessionId) {
        return Session.find("id", sessionId).stream().findFirst();
    }

    public Optional<Session> findByIdWithEnrichedSpeakers(String sessionId) {
        Optional<Session> result = Session.find("id", sessionId).stream().findFirst();

        // Fake delay!
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        List<SpeakerFromService> allSpeakers = speakerService.listAll();
        Session session = result.get();
        for (var speaker : session.speakers) {
            enrichSpeaker(allSpeakers, speaker);
        }
        return result;
    }
    
	public void addSpeakerToSession(final String speakerName, final Session session) {
        Speaker speaker = getOrCreateSpeakerByName(speakerName);
        session.addSpeaker(speaker);
        repository.persist(session);
	}

    public Speaker getOrCreateSpeakerByName(final String speakerName) {
        // Feature Toggle
        if (sessionIntegration) {
            return getSpeakerByNameFromService(speakerName).orElseThrow(() -> new NotFoundException());
        } else {
            return getSpeakerByNameLocally(speakerName).orElse(Speaker.from(speakerName));
        }
    }
}
