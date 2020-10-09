package org.acme.conference.session;

import java.util.UUID;

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

    @Column(unique = true)
    public String uuid; 

    public static Speaker from (String speakerName) {
        Speaker speaker = new Speaker();
        speaker.name=speakerName;
        speaker.uuid=UUID.randomUUID().toString();
        return speaker;
    }

	public static Speaker from(SpeakerFromService speakerFromService) {
        Speaker speaker = new Speaker();
        enrichFromService(speakerFromService, speaker);
        return speaker;
    }

    public static void enrichFromService(SpeakerFromService speakerFromService, Speaker speaker) {
        speaker.name=speakerFromService.nameFirst+" "+speakerFromService.nameLast;
        speaker.uuid=speakerFromService.uuid;
    }

}
