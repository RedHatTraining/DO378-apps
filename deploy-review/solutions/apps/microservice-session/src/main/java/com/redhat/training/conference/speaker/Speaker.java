package com.redhat.training.conference.speaker;

public class Speaker {

    public int id;

    public String name;

    public String organization;

    public Speaker() {
    }

    public Speaker(int id, String name, String organization) {
        this.id = id;
        this.name = name;
        this.organization = organization;
    }
}