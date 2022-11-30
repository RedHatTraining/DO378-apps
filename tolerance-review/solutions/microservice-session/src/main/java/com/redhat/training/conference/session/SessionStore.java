package com.redhat.training.conference.session;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class SessionStore {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public SessionRepository repository;

    @Inject
    public SpeakerRepository speakerRepository;

    @Inject
    @RestClient
    SpeakerService speakerService;

    public int counter = 1;

    public SessionStore() {
    }

    public Collection<Session> findAll() {
        return findAllWithEnrichment();
    }

    public Collection<Session> findAllWithEnrichment(){
        List<Session> sessions = repository.findAll().list();
        final List<SpeakerFromService> allSpeakers = speakerService.listAll();
        sessions.stream().flatMap(s->s.speakers.stream()).forEach(sp->this.enrichSpeaker(allSpeakers, sp));
        return sessions;
}

    public Collection<Session> findAllWithoutEnrichment(){
        return repository.findAll().list();
    }

    @Transactional
    public Session save(Session session) {
        repository.persist(session);
        return session;
    }

    @Transactional
    public Optional<Session> updateById(String sessionId, Session newSession) {
        Optional<Session> sessionOld = findById(sessionId);
        if (!sessionOld.isPresent()) {
            return Optional.empty();
        }

        sessionOld.ifPresent(ses -> {
            ses.schedule = newSession.schedule;
            repository.persist(ses);
        });

        return sessionOld;
    }

    public Optional<Session> findById(String sessionId) {
        return findByIdWithEnrichedSpeakers(sessionId);
    }

    public Optional<Session> findByIdWithoutEnrichment(String sessionId) {
        return repository.find("id", sessionId).stream().findFirst();
    }

    public Optional<Session> findByIdWithoutEnrichmentMaybeFail(String sessionId) {
        if(counter % 5 == 0) {
            logger.info("Success");
            return repository.find("id", sessionId).stream().findFirst();
        } else {
            logger.warn("Fail, please retry");
            this.counter += 1;
            throw new InternalServerErrorException();
        }
    }

    public Optional<Session> findByIdWithEnrichedSpeakers(String sessionId) {
        Optional<Session> result = repository.find("id", sessionId).stream().findFirst();

        List<SpeakerFromService> allSpeakers = null;

        // Simulate delay
        try {
            Thread.sleep(10000);
            allSpeakers = speakerService.listAll();
        } catch (Exception e) {}


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

	public void addSpeakerToSession(final String speakerName, final Session session) {
        Speaker speaker = getOrCreateSpeakerByName(speakerName);
        session.addSpeaker(speaker);
        repository.persist(session);
	}

	public void removeSpeakerFromSession(final String speakerName, final Session session) {
        Speaker speaker = getOrCreateSpeakerByName(speakerName);
        session.removeSpeaker(speaker);
	    repository.persist(session);
	}

    public Speaker getOrCreateSpeakerByName(final String speakerName) {
        return getSpeakerByNameLocally(speakerName).orElse(Speaker.from(speakerName));
    }

    private Optional<Speaker> getSpeakerByNameLocally(final String speakerName) {
        var speakers = speakerRepository.find("name", speakerName);
        return speakers.stream().findFirst();
    }

    private Optional<Speaker> getSpeakerByNameFromService(final String speakerName) {
        Optional<SpeakerFromService> serviceSpeaker = speakerService.search(speakerName, "uuid").stream().findFirst();
        Optional<Speaker> localSpeaker = getSpeakerByNameLocally(speakerName);

        if (localSpeaker.isPresent()){
            Speaker speaker = localSpeaker.get();
            if (serviceSpeaker.isPresent()){
                Speaker.enrichFromService(serviceSpeaker.get(), speaker);
            }
            return localSpeaker;
        } else {
            if (serviceSpeaker.isPresent()){
                return Optional.of(Speaker.from(serviceSpeaker.get()));
            } else {
                return Optional.empty();
            }
        }

    }
}
