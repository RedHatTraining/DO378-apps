package com.redhat.training.conference.session;

import com.redhat.training.conference.speaker.SpeakerServiceClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SessionStoreService {

    @Inject
    @RestClient
    SpeakerServiceClient speakerService;

    public List<SessionWithSpeaker> getAll() throws Exception {
        List<Session> sessions = Session.findAll().list();

        return sessions.stream()
                .map(this::toSessionWithSpeaker)
                .collect(Collectors.toList());
    }

    public SessionWithSpeaker getById(Long sessionId) {
        Optional<Session> optional = Session.findByIdOptional(sessionId);
        Session session = optional.orElseThrow(NotFoundException::new);

        return toSessionWithSpeaker(session);
    }

    @Transactional
    public Response save(Session newSession, @Context UriInfo uriInfo) {
        newSession.persist();

        return Response.created(generateUriForSession(newSession, uriInfo))
                .header("id", newSession.id)
                .build();
    }

    private SessionWithSpeaker toSessionWithSpeaker(Session session) {
        return session.withSpeaker(speakerService.getSpeaker(session.speakerId));
    }

    private URI generateUriForSession(Session session, UriInfo uriInfo) {
        return uriInfo.getAbsolutePathBuilder()
                .path("/{id}").build(session.id);
    }
}
