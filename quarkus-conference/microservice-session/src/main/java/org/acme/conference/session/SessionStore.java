package org.acme.conference.session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SessionStore {

    @Inject
    private SessionRepository repository;

    @ConfigProperty(name = "features.session-integration", defaultValue = "false")
    boolean sessionIntegration;

    @Inject
    @RestClient
    SpeakerService speakerService;

    public SessionStore() {
    }

    public Collection<Session> findAll() {
        List<Session> sessions = repository.findAll().list();
        // Feature toggle
        if (sessionIntegration){
            final List<SpeakerFromService> allSpeakers = speakerService.listAll();
            sessions.stream().flatMap(s->s.speakers.stream()).forEach(sp->this.enrichSpeaker(allSpeakers, sp));
        }
        return sessions;
    }

    @Transactional
    public Session save(Session session) {
        repository.persist(session);
        return session;
    }

    @Transactional
    public Optional<Session> updateById(String sessionId, Session session) {
        Optional<Session> sessionOld = findById(sessionId);
        if (!sessionOld.isPresent()) {
            return Optional.empty();
        }

        sessionOld.ifPresent(s -> {
            s.schedule = session.schedule;
            s.speakers.clear();
            s.speakers.addAll(session.speakers);
            repository.persist(s);
        });
        return Optional.ofNullable(session);
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
        return repository.find("id", sessionId).stream().findFirst();
    }

    public Optional<Session> findByIdWithEnrichedSpeakers(String sessionId) {
        Optional<Session> result = repository.find("id", sessionId).stream().findFirst();

        List<SpeakerFromService> allSpeakers = speakerService.listAll();
        Session session = result.get();
        for (var speaker : session.speakers) {
            enrichSpeaker(allSpeakers, speaker);
        }
        return result;
    }

    private void enrichSpeaker(List<SpeakerFromService> allSpeakers, Speaker speaker) {
        var serviceSpeaker = allSpeakers.stream().filter(s -> s.uuid.equals(speaker.uuid)).findFirst();
        if (serviceSpeaker.isPresent()) {
            var speakerFromService = serviceSpeaker.get();
            Speaker.enrichFromService(speakerFromService, speaker);
        }
    }

    @Transactional
    public Optional<Session> deleteById(String sessionId) {
        Optional<Session> session = findById(sessionId);
        if (!session.isPresent()) {
            return Optional.empty();
        }
        repository.delete(session.get());
        return session;
    }

	public void addSpeakerToSession(final String sessionId, final String speakerName, final Session session) {
	    final Collection<Speaker> speakers = session.speakers;
	    Speaker speaker = getSpeakerByName(speakerName);
        speakers.add(speaker);
	    updateById(sessionId, session);
	}

	public void removeSpeakerFromSession(final String sessionId, final String speakerName, final Session session) {
	    final Collection<Speaker> speakers = session.speakers;
	    Speaker speaker = getSpeakerByName(speakerName);
        speakers.remove(speaker);
	    updateById(sessionId, session);
	}

    public Speaker getSpeakerByName(final String speakerName) {
        // Feature Toggle
        if (sessionIntegration) {
            return getSpeakerByNameFromService(speakerName);
        } else {
            return Speaker.from(speakerName);
        }
    }

    private Speaker getSpeakerByNameFromService(final String speakerName) {
        Collection<SpeakerFromService> speakersByName = speakerService.search(speakerName, "uuid");
        if (speakersByName.size()==1){
            return Speaker.from(speakersByName.iterator().next());
        } else {
            throw new NotFoundException();
        }
    }
}
