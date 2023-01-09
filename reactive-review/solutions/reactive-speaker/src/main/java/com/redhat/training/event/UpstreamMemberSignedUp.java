package com.redhat.training.event;

public class UpstreamMemberSignedUp {
    Long speakerId;
    String fullName;
    String email;
    public UpstreamMemberSignedUp() {}

    public UpstreamMemberSignedUp(Long speakerId, String fullName, String email) {
        this.speakerId = speakerId;
        this.fullName = fullName;
        this.email = email;
    }
}
