package com.redhat.training.speaker;

import java.util.Collection;
import java.util.Optional;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.panache.common.Sort;

@Path( "/speaker" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SpeakerResource {

    private static final int SEARCH_MINIMUM_CHARS = 3;

    @Inject
    SpeakerDAO speakerDAO;

    @GET
    public Collection<Speaker> listAll() {
        return Speaker.listAll();
    }

    @GET
    @Path( "/sorted" )
    public Collection<Speaker> listAllSorted( @QueryParam( "sort" ) String sortField ) {
        return speakerDAO.findAll( sortBy( sortField ) );
    }

    private Sort sortBy( String sortField ) {
        return Optional.ofNullable( sortField ).map( Sort::by ).orElse( Sort.by( "nameLast" ) );
    }

    @GET
    @Path( "/{uuid}" )
    public Speaker findByUuid( @PathParam( "uuid" ) String uuid ) {
        return speakerDAO.getByUuid( uuid )
                .orElseThrow( NotFoundException::new );
    }

    @GET
    @Path( "/search" )
    public Collection<Speaker> search( @QueryParam( "query" ) String query, @QueryParam( "sort" ) String sort ) {
        String queryValid = Optional.ofNullable( query )
                .filter( q -> q.length() >= SEARCH_MINIMUM_CHARS )
                .orElseThrow( () -> new BadRequestException( "Insufficient number of chars" ) );

        return speakerDAO.search( queryValid, sortBy( sort ) );
    }

    @Transactional
    @POST
    public Speaker insert( Speaker speaker ) {
        return speakerDAO.create( speaker );
    }

    @Transactional
    @PUT
    @Path( "/{uuid}" )
    public Speaker update( @PathParam( "uuid" ) String uuid, Speaker speaker ) {
        if ( null == uuid || null == speakerDAO.getByUuid( uuid ) ) {
            throw new NotFoundException();
        }

        if ( null == speaker || !uuid.equals( speaker.uuid ) ) {
            throw new BadRequestException();
        }

        speaker.uuid = uuid;
        return speakerDAO.update( speaker );
    }

    @Transactional
    @DELETE
    @Path( "/{uuid}" )
    public void remove( @PathParam( "uuid" ) String uuid ) {

        Speaker speaker = Optional.ofNullable( uuid )
                .flatMap( speakerDAO::getByUuid )
                .orElseThrow( NotFoundException::new );

        speakerDAO.delete( speaker );
    }

}
