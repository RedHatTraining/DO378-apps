package com.redhat.training.conference.session;

import javax.persistence.Entity;
import com.redhat.training.conference.speaker.Speaker;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Session extends PanacheEntity {

    public int schedule;

    public int speakerId;

    public SessionWithSpeaker withSpeaker( final Speaker speaker ) {
        return new SessionWithSpeaker( id, schedule, speaker );
    }

}
