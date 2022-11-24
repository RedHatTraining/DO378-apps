package com.redhat.training.conference.session;

import com.redhat.training.conference.speaker.Speaker;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Session extends PanacheEntity {

    public String sessionTitle;

    public long speakerId;

    public SessionWithSpeaker withSpeaker(final Speaker speaker) {
        return new SessionWithSpeaker(id, sessionTitle, speaker);
    }
}
