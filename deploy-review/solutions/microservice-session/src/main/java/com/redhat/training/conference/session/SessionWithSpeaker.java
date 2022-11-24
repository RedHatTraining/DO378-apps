package com.redhat.training.conference.session;

import com.redhat.training.conference.speaker.Speaker;

public class SessionWithSpeaker {

    public Long id;

    public String title;

    public Speaker speaker;

    public SessionWithSpeaker(Long id, String title, Speaker speaker) {
        this.id = id;
        this.title = title;
        this.speaker = speaker;
    }
}
