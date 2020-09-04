package org.acme.conference.session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Speaker {

    @Id
    @GeneratedValue
    public long id;

    @Column(unique = true)
    public String name;

    public static Speaker from (String speakerName) {
        Speaker speaker = new Speaker();
        speaker.name=speakerName;
        return speaker;
    }

}
