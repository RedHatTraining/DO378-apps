package com.redhat.training;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.micrometer.core.annotation.Counted;

@Path( "sessions" )
@ApplicationScoped
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class SessionResource {

    @Inject
    SessionStore sessionStore;

    @GET
    @Counted( value = "callsToGetSessions" )
    public Collection<Session> getAllSessions() throws Exception {
        return sessionStore.findAll();
    }

    @GET
    @Path( "/{sessionId}" )
    public Response getSession( @PathParam( "sessionId" ) final String sessionId ) {
        final Optional<Session> result = sessionStore.findById( sessionId );

        return result.map( s -> Response.ok( s ).build() ).orElseThrow( NotFoundException::new );
    }

    // @POST
    // public Session createSession(final Session session) {
    // return sessionStore.save(session);
    // }

    // public Response retrieveSessionFallback(final String sessionId) {
    // logger.warn("Fallback for GET /sessions/"+sessionId);
    // return sessionStore.findByIdWithoutEnrichment(sessionId)
    // .map(s -> Response.ok(s).build())
    // .orElseThrow(NotFoundException::new);
    // }

    // @GET
    // @Path("/{sessionId}")
    // public Response retrieveSession(@PathParam("sessionId") final String
    // sessionId) {
    // final Optional<Session> result = sessionStore.findById(sessionId);

    // return result.map(s ->
    // Response.ok(s).build()).orElseThrow(NotFoundException::new);
    // }

    // @PUT
    // @Path("/{sessionId}")
    // public Response updateSession(@PathParam("sessionId") final String sessionId,
    // final Session session) {
    // final Optional<Session> updated = sessionStore.updateById(sessionId,
    // session);
    // return updated.map(s ->
    // Response.ok(s).build()).orElseThrow(NotFoundException::new);
    // }

    // @DELETE
    // @Path("/{sessionId}")
    // public Response deleteSession(@PathParam("sessionId") final String sessionId)
    // {
    // final Optional<Session> removed = sessionStore.deleteById(sessionId);
    // return removed.map(s ->
    // Response.noContent().build()).orElseThrow(NotFoundException::new);
    // }

    // @GET
    // @Path("/{sessionId}/speakers")
    // public Response sessionSpeakers(@PathParam("sessionId") final String
    // sessionId) {
    // Optional<Session> session;
    // try {
    // session = findSessionSpeakers(sessionId);
    // } catch (Exception e) {
    // logger.warn("Falling back to no enrichment");
    // session = sessionStore.findByIdWithoutEnrichment(sessionId);
    // }
    // return session.map(s -> s.speakers).map(l ->
    // Response.ok(l).build()).orElseThrow(NotFoundException::new);
    // }

    // public Optional<Session> findSessionSpeakers(String sessionId) {
    // return sessionStore.findById(sessionId);
    // }

    // @PUT
    // @Path("/{sessionId}/speakers/{speakerName}")
    // @Transactional
    // public Response addSessionSpeaker(@PathParam("sessionId") final String
    // sessionId,
    // @PathParam("speakerName") final String speakerName) {
    // final Optional<Session> result =
    // sessionStore.findByIdWithoutEnrichmentMaybeFail(sessionId);

    // if (result.isPresent()) {
    // final Session session = result.get();
    // sessionStore.addSpeakerToSession(speakerName, session);
    // return Response.ok(session).build();
    // }

    // throw new NotFoundException();
    // }

    // @DELETE
    // @Path("/{sessionId}/speakers/{speakerName}")
    // @Transactional
    // public Response removeSessionSpeaker(@PathParam("sessionId") final String
    // sessionId,
    // @PathParam("speakerName") final String speakerName) {
    // final Optional<Session> result =
    // sessionStore.findByIdWithoutEnrichment(sessionId);

    // if (result.isPresent()) {
    // final Session session = result.get();
    // sessionStore.removeSpeakerFromSession(speakerName, session);
    // return Response.ok(session).build();
    // }

    // throw new NotFoundException();
    // }
}
