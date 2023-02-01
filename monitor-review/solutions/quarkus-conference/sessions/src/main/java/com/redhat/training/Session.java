package com.redhat.training;

import java.util.HashSet;
import java.util.Set;

public class Session {

    public String id;
    public Set<Speaker> speakers;

    Session( String id ) {
        this.id = id;
        speakers = new HashSet<>();
    }

    public static Session fromId( String id ) {
        return new Session( id );
    }

    public Session addSpeaker( final Speaker speaker ) {
        if ( !speakers.contains( speaker ) )
            speakers.add( speaker );

        return this;
    }

    public Session removeSpeaker( final Speaker speaker ) {
        if ( speakers.contains( speaker ) )
            speakers.remove( speaker );

        return this;
    }
}
