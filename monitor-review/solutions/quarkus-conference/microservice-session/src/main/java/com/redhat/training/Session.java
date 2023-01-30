package com.redhat.training;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


@Entity
public class Session {

    @Id
    public String id;

    public int schedule;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Speaker> speakers = new HashSet<>();

    public void addSpeaker(final Speaker speaker){
        if (speakers.contains(speaker))
            return;

        speakers.add(speaker);
        speaker.addSession(this);
    }

    public void removeSpeaker(final Speaker speaker){
        if (!speakers.contains(speaker))
            return;

        speakers.remove(speaker);
        speaker.removeSession(this);
    }
}
