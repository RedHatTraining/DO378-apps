package com.redhat.training.speaker;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.redhat.training.speaker.idgenerator.IdGenerator;

import io.quarkus.panache.common.Sort;

/**
 * Speaker Data Access Object
 */
@ApplicationScoped
public class SpeakerDAO {

    @Inject
    IdGenerator generator;

    public Speaker create( Speaker speaker ) {
        speaker.uuid = generator.generate();
        speaker.persist();
        return speaker;
    }

    public Collection<Speaker> findAll( Sort sort ) {
        return Speaker.findAll( sort ).list();
    }

    public Speaker update( Speaker updated ) {
        updated.persist();
        return updated;
    }

    public void delete( Speaker speaker ) {
        speaker.delete();
    }

    public Optional<Speaker> getByUuid( String uuid ) {
        return Speaker.find( "uuid", uuid ).firstResultOptional();
    }

    public Collection<Speaker> search( String query, Sort sort ) {
        Objects.requireNonNullElse( query, "UNKNOWNUNKNOWN" );
        Map<String, Object> queryValue = Collections.singletonMap( "query", query.toUpperCase().concat( "%" ) );
        return Speaker.find( "upper(nameFirst) like :query or upper(nameLast) like :query", sort, queryValue ).list();
    }

}
