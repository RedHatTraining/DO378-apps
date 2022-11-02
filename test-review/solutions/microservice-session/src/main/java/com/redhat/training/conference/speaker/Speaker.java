package com.redhat.training.conference.speaker;

public class Speaker {
    public int id;
    public String firstName;
    public String lastName;

    public Speaker( int id, String firstName, String lastName ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}