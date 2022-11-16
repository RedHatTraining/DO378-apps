package com.redhat.training.conference.session;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.redhat.training.conference.speaker.SpeakerService;

import javax.ws.rs.core.*;

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
    public Response save( Session newSession,@Context UriInfo uriInfo ) {
        newSession.persist();

        return Response.created(generateUriForSession(newSession, uriInfo))
        .header("id", newSession.id)
        .build();
    }

    private SessionWithSpeaker toSessionWithSpeaker( Session session ) {
        var speaker = speakerService.getById( session.speakerId );

        return session.withSpeaker( speaker );
    }

    private URI generateUriForSession(Session session, UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder().path("/{id}").build(session.id);
    }

}
