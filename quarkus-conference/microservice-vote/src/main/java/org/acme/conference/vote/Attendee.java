package org.acme.conference.vote;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.bson.codecs.pojo.annotations.BsonId;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

/**
 * Attendee entity
 * 
 * @author jzuriaga
 *
 */
public class Attendee extends PanacheMongoEntity {

    //@BsonId
    private String uuid;

    @NotBlank
    private String name;

    public String getId () {
        return uuid;
    }

    public void setId (String id) {
        this.uuid = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return "Attendee [id=" + uuid + ", name=" + name + "]";
    }

    @Override
    public int hashCode () {
        return Objects.hash(uuid, name);
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Attendee other = (Attendee) obj;
        if (!Objects.equals(this.uuid, other.uuid) || !Objects.equals(this.name, other.name))
            return false;
        return true;
    }

}
