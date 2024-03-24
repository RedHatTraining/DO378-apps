package com.redhat.training;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import io.opentelemetry.instrumentation.annotations.WithSpan;

@Path( "/speaker" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SpeakerResource {

    @Inject
    SpeakerFinder finder;

    @GET
    @WithSpan
    public Collection<Speaker> listAll() {
        return finder.all();
    }

    @GET
    @Path( "/sorted" )
    public Collection<Speaker> listAllSorted( @QueryParam( "sort" ) String sortField ) {
        return finder.allSorted( sortField );
    }

    @GET
    @Path( "/{uuid}" )
    public Speaker findByUuid( @PathParam( "uuid" ) String uuid ) {
        return getByUuid( uuid )
                .orElseThrow( NotFoundException::new );
    }

    public Optional<Speaker> getByUuid( String uuid ) {
        return Speaker.find( "uuid", uuid ).firstResultOptional();
    }

    @Transactional
    @POST
    public Speaker insert( Speaker speaker ) {
        return create( speaker );
    }

    private SpeakerIdGenerator generator = new SpeakerIdGenerator();

    public Speaker create( Speaker speaker ) {
        speaker.uuid = generator.generate();
        speaker.persist();
        return speaker;
    }

    @Transactional
    @PUT
    @Path( "/{uuid}" )
    public Speaker update( @PathParam( "uuid" ) String uuid, Speaker speaker ) {
        if ( null == uuid || null == getByUuid( uuid ) ) {
            throw new NotFoundException();
        }

        if ( null == speaker || !uuid.equals( speaker.uuid ) ) {
            throw new BadRequestException();
        }

        speaker.uuid = uuid;
        return update( speaker );
    }

    public Speaker update( Speaker updated ) {
        updated.persist();
        return updated;
    }

    private static class SpeakerIdGenerator {

        public String generate() {
            return UUID.randomUUID().toString();
        }
    }

}
