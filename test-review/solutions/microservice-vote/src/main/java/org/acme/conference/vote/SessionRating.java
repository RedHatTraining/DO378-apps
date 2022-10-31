package org.acme.conference.vote;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import io.quarkus.mongodb.panache.PanacheMongoEntity;


public class SessionRating extends PanacheMongoEntity {

    private String ratingId;

    @NotNull
    private String session;

    @NotNull
    private String attendeeId;

    private int rating;

    public String getRatingId () {
        return ratingId;
    }

    public void setRatingId (String id) {
        this.ratingId = id;
    }

    public String getSession () {
        return session;
    }

    public void setSession (String session) {
        this.session = session;
    }

    public String getAttendeeId () {
        return attendeeId;
    }

    public void setAttendeeId (String attendeeId) {
        this.attendeeId = attendeeId;
    }

    public int getRating () {
        return rating;
    }

    public void setRating (int rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode () {
        return Objects.hash(attendeeId, session, ratingId);
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionRating other = (SessionRating) obj;
        if (attendeeId == null) {
            if (other.attendeeId != null)
                return false;
        } else if (!attendeeId.equals(other.attendeeId))
            return false;
        if (ratingId == null) {
            if (other.ratingId != null)
                return false;
        } else if (!ratingId.equals(other.ratingId))
            return false;
        if (rating != other.rating)
            return false;
        if (session == null) {
            if (other.session != null)
                return false;
        } else if (!session.equals(other.session))
            return false;
        return true;
    }

    @Override
    public String toString () {
        return new StringBuilder(this.getClass()
                .getSimpleName()).append(" [")
                        .append("ratingId=")
                        .append(ratingId)
                        .append(", ")
                        .append("session=")
                        .append(session)
                        .append(", ")
                        .append("attendeeId=")
                        .append(attendeeId)
                        .append("]")
                        .toString();
    }
}
