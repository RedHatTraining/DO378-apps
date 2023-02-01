package com.redhat.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SessionRepository {

    // In-memory repository
    List<Session> sessions = new ArrayList<>();

    public SessionRepository() {
        sessions.add(
                Session
                        .fromId( "session-1" )
                        .addSpeaker( Speaker.fromUUID( "s-1-1" ) )
                        .addSpeaker( Speaker.fromUUID( "s-1-2" ) ) );
        sessions.add(
                Session
                        .fromId( "session-2" )
                        .addSpeaker( Speaker.fromUUID( "s-1-3" ) ) );
    }

    public List<Session> findAll() {
        return sessions;
    }

    public Optional<Session> findById( String sessionId ) {
        return sessions.stream()
                .filter( session -> session.id.equals( sessionId ) )
                .findAny();
    }

    // public Collection<Session> findAllWithEnrichment() {

    // for (Session session : sessions) {
    // for (Speaker speaker : session.speakers) {
    // speaker.uuid
    // }
    // }
    // final List<SpeakerFromService> allSpeakers = speakerService.listAll();
    // sessions.stream().flatMap(s->s.speakers.stream()).forEach(sp->this.enrichSpeaker(allSpeakers,
    // sp));
    // return sessions;
    // }

}
