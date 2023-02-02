package com.redhat.training;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.logging.Log;

@ApplicationScoped
public class SessionStore {

    @Inject
    public SessionRepository repository;

    @Inject
    @RestClient
    SpeakerService speakerService;

    public Collection<Session> findAll() {
        Log.info( "Finding all sessions" );
        return findAllSessionsWithSpeakerInfo();
    }

    public Collection<Session> findAllSessionsWithSpeakerInfo() {
        List<Session> sessions = repository.findAll();
        Log.debug( "Gathering extended speaker information from 'speakers' service" );
        List<SpeakerFromService> allSpeakers = speakerService.listAll();
        sessions.stream().flatMap( s -> s.speakers.stream() ).forEach( sp -> this.enrichSpeaker( allSpeakers, sp ) );
        Log.debug( "Added speakers information to session list" );
        return sessions;
    }

    private void enrichSpeaker( List<SpeakerFromService> allSpeakers, Speaker speaker ) {
        var serviceSpeaker = allSpeakers.stream().filter( s -> s.uuid.equals( speaker.uuid ) ).findFirst();
        if ( serviceSpeaker.isPresent() ) {
            var speakerFromService = serviceSpeaker.get();
            Speaker.enrichFromService( speakerFromService, speaker );
        }
    }

    public Optional<Session> findById( String sessionId ) {
        return findByIdWithEnrichedSpeakers( sessionId );
    }

    public Optional<Session> findByIdWithEnrichedSpeakers( String sessionId ) {
        Optional<Session> result = repository.findById( sessionId ).stream().findFirst();
        Session session = result.get();

        List<SpeakerFromService> allSpeakers = speakerService.listAll();

        for ( var speaker : session.speakers ) {
            enrichSpeaker( allSpeakers, speaker );
        }
        return result;
    }

    public Optional<Session> findByIdWithoutEnrichment( String sessionId ) {
        return repository.findById( sessionId ).stream().findFirst();
    }
}
