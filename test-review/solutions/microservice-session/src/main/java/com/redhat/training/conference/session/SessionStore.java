package com.redhat.training.conference.session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.redhat.training.conference.speaker.SpeakerService;

@ApplicationScoped
public class SessionStore {

    @Inject
    @RestClient
    SpeakerService speakerService;

    public List<SessionWithSpeaker> getAll() throws Exception {
        List<Session> sessions = Session.findAll().list();

        return sessions.stream()
                .map( this::toSessionWithSpeaker )
                .collect( Collectors.toList() );
    }

    public SessionWithSpeaker getById( Long sessionId ) {
        Optional<Session> optional = Session.findByIdOptional( sessionId );
        Session session = optional.orElseThrow( () -> new NotFoundException() );

        return toSessionWithSpeaker( session );
    }

    @Transactional
    public Session save( Session session ) {
        session.persist();
        return session;
    }

    private SessionWithSpeaker toSessionWithSpeaker( Session session ) {
        var speaker = speakerService.getById( session.speakerId );
        return session.withSpeaker( speaker );
    }

}
