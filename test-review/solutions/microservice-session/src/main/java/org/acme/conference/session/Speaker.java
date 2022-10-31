package org.acme.conference.session;

import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import javax.json.bind.annotation.JsonbTransient;

@Entity
public class Speaker {

    @Id
    @GeneratedValue
    public long id;

    @Column(unique = true)
    public String name;

    @Column(unique = true)
    public String uuid; 

    @ManyToMany(mappedBy="speakers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Transient
    @JsonbTransient
    public Set<Session> sessions = new HashSet<>();

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
    
    public void addSession(final Session session){
        if (sessions.contains(session))
            return;

        sessions.add(session);
        session.addSpeaker(this);
    }

    public void removeSession(final Session session){
        if (!sessions.contains(session))
            return;

        sessions.remove(session);
        session.removeSpeaker(this);
    }
}
