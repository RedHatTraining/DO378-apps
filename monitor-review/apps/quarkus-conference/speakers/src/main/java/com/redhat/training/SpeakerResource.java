package com.redhat.training;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Path( "/speaker" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SpeakerResource {

    @Inject
    SpeakerFinder finder;

    @GET
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
