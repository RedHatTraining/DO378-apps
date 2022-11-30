package com.redhat.training.conference.session;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.Retry;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * SessionResource
 */
@Path("sessions")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    SessionStore sessionStore;

    public Collection<Session> allSessionsFallback() throws Exception {
      logger.warn("Fallback for GET /sessions");
      return null;
    }

    public Response retrieveSessionFallback(final String sessionId) {
      logger.warn("Fallback for GET /sessions/"+sessionId);
      return null;
    }

    @GET
    public Collection<Session> allSessions() throws Exception {
        return sessionStore.findAll();
    }

    @POST
    public Session createSession(final Session session) {
        return sessionStore.save(session);
    }

    @GET
    @Path("/{sessionId}")
    public Response retrieveSession(@PathParam("sessionId") final String sessionId) {
        final Optional<Session> result = sessionStore.findById(sessionId);

        return result.map(s -> Response.ok(s).build()).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/{sessionId}")
    public Response updateSession(@PathParam("sessionId") final String sessionId, final Session session) {
        final Optional<Session> updated = sessionStore.updateById(sessionId, session);
        return updated.map(s -> Response.ok(s).build()).orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("/{sessionId}")
    public Response deleteSession(@PathParam("sessionId") final String sessionId) {
        final Optional<Session> removed = sessionStore.deleteById(sessionId);
        return removed.map(s -> Response.noContent().build()).orElseThrow(NotFoundException::new);
    }

    @GET
    @Path("/{sessionId}/speakers")
    public Response sessionSpeakers(@PathParam("sessionId") final String sessionId) {
        Optional<Session> session;
        try {
            session = findSessionSpeakers(sessionId);
        } catch (TimeoutException | ProcessingException e) {
            logger.warn("Falling back to no enrichment");
            session = sessionStore.findByIdWithoutEnrichment(sessionId);
        }
        return session.map(s -> s.speakers).map(l -> Response.ok(l).build()).orElseThrow(NotFoundException::new);
    }

    public Optional<Session> findSessionSpeakers(String sessionId) {
        return sessionStore.findById(sessionId);
    }

    @PUT
    @Path("/{sessionId}/speakers/{speakerName}")
    @Transactional
    public Response addSessionSpeaker(@PathParam("sessionId") final String sessionId,
            @PathParam("speakerName") final String speakerName) {
        final Optional<Session> result = sessionStore.findByIdWithoutEnrichmentMaybeFail(sessionId);

        if (result.isPresent()) {
            final Session session = result.get();
            sessionStore.addSpeakerToSession(speakerName, session);
            return Response.ok(session).build();
        }

        throw new NotFoundException();
    }

    @DELETE
    @Path("/{sessionId}/speakers/{speakerName}")
    @Transactional
    public Response removeSessionSpeaker(@PathParam("sessionId") final String sessionId,
            @PathParam("speakerName") final String speakerName) {
        final Optional<Session> result = sessionStore.findByIdWithoutEnrichment(sessionId);

        if (result.isPresent()) {
            final Session session = result.get();
            sessionStore.removeSpeakerFromSession(speakerName, session);
            return Response.ok(session).build();
        }

        throw new NotFoundException();
    }
}
