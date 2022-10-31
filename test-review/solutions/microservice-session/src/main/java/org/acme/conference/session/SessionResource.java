package org.acme.conference.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.Retry;

/**
 * SessionResource
 */
@Path("sessions")
@ApplicationScoped
public class SessionResource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    SessionStore sessionStore;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(fallbackMethod = "allSessionsFallback", applyOn = { Exception.class })
    public Collection<Session> allSessions() throws Exception {
        return sessionStore.findAll();
    }

    public Collection<Session> allSessionsFallback() throws Exception {
        logger.warn("Fallback sessions");
        return sessionStore.findAllWithoutEnrichment();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Session createSession(final Session session) {
        return sessionStore.save(session);
    }

    @GET
    @Path("/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(fallbackMethod = "retrieveSessionFallback")
    @CircuitBreaker(requestVolumeThreshold = 2, failureRatio = 1, delay = 30_000)
    public Response retrieveSession(@PathParam("sessionId") final String sessionId) {
        final Optional<Session> result = sessionStore.findById(sessionId);
        return result.map(s -> Response.ok(s).build()).orElseThrow(NotFoundException::new);
    }

    public Response retrieveSessionFallback(final String sessionId) {
        logger.warn("Fallback session");
        final Optional<Session> result = sessionStore.findByIdWithoutEnrichment(sessionId);
        return result.map(s -> Response.ok(s).build()).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/{sessionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Bulkhead(1)
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
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(2000)
    public Response sessionSpeakers(@PathParam("sessionId") final String sessionId) {
        final Optional<Session> session = sessionStore.findById(sessionId);
        return session.map(s -> s.speakers).map(l -> Response.ok(l).build()).orElseThrow(NotFoundException::new);
    }

    @PUT
    @Path("/{sessionId}/speakers/{speakerName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 60, delay = 1_000)
    public Response addSessionSpeaker(@PathParam("sessionId") final String sessionId,
            @PathParam("speakerName") final String speakerName) {
        final Optional<Session> result = sessionStore.findByIdWithoutEnrichment(sessionId);

        if (result.isPresent()) {
            final Session session = result.get();
            sessionStore.addSpeakerToSession(speakerName, session);
            return Response.ok(session).build();
        }

        throw new NotFoundException();
    }

    @DELETE
    @Path("/{sessionId}/speakers/{speakerName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 60, delay = 1_000)
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
