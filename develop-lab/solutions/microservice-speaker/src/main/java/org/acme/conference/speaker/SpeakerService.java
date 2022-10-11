package org.acme.conference.speaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SpeakerService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Speaker create (Speaker speaker) {
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

    public Collection<Speaker> search(String query, Sort sort) {
        Objects.requireNonNullElse(query, "UNKNOWNUNKNOWN");
        Map<String, Object> queryValue = Collections.singletonMap("query",query.toUpperCase().concat("%"));
        return Speaker.find("upper(nameFirst) like :query or upper(nameLast) like :query", sort, queryValue).list();
    }

    public List<Speaker> getActiveSpeakers() {
        PanacheQuery<Speaker> activeSpeakers = Speaker.findAll();
        return activeSpeakers.page(Page.ofSize(10)).list();
    }
}
