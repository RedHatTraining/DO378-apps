package org.acme.conference.speaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

/**
 * SpeakerDAO
 */
@ApplicationScoped
public class SpeakerDAO {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SpeakerIdGenerator generator = new SpeakerIdGenerator();

    public Speaker create (Speaker speaker) {
        //speaker.uuid=generator.generate();
        speaker.persist();
        return speaker;
    }

    public Collection<Speaker> findAll(Sort sort) {
        return Speaker.findAll(sort).list();
    }

    public Speaker update (Speaker updated) {
        updated.persist();
        return updated;
    }

    public void delete (Speaker speaker) {
        speaker.delete();
    }

    public Optional<Speaker> getByUuid(String uuid) {
        return Speaker.find("uuid", uuid).firstResultOptional();
    }

    private static class SpeakerIdGenerator {

        public String generate() {
            return UUID.randomUUID().toString();
        }
    }

    public Collection<Speaker> search(String query, Sort sort) {
        Objects.requireNonNullElse(query, "UNKNOWNUNKNOWN");
        Map<String, Object> queryValue = Collections.singletonMap("query",query.toUpperCase().concat("%"));
        return Speaker.find("upper(nameFirst) like :query or upper(nameLast) like :query", sort, queryValue).list();
    }

    public List<Speaker> getActiveSpeakers() {
        PanacheQuery<Speaker> activeSpeakers = Speaker.findAll();
        return activeSpeakers.page(Page.ofSize(20)).list();
    }

}