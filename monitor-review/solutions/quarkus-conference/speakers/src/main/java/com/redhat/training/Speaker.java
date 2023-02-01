package com.redhat.training;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.logging.Log;

/**
 * Speaker
 */
@Entity
public class Speaker extends PanacheEntity {

    public String uuid;

    public String nameFirst;
    public String nameLast;
    public String organization;
    public String biography;
    public String picture;
    public String twitterHandle;

    @Transient
    public Map<String, String> links = new HashMap<>();

    public static List<Speaker> getAll() {
        Log.info("Retrieving all speakers from database");

        // Simulate delay
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}

        return Speaker.listAll();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( this.getClass().getSimpleName() );
        sb.append( "[" );
        sb.append( "<" ).append( id ).append( ">" );
        sb.append( "," );
        sb.append( "nameFirst=" ).append( nameFirst );
        sb.append( "," );
        sb.append( "nameLast=" ).append( nameLast );
        sb.append( "]" );
        return sb.toString();
    }
}
