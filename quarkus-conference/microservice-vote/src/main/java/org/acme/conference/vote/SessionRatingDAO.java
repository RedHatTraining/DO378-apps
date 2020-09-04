package org.acme.conference.vote;

import java.util.Collection;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class SessionRatingDAO {

    private final RatingIdGenerator generator = new RatingIdGenerator();

    public SessionRating rateSession (SessionRating sessionRating) {
        sessionRating.setRatingId(generator.nextValue());
        sessionRating.persist();
        return sessionRating;
    }

    public Collection<SessionRating> getAllRatings () {
        return SessionRating.findAll()
                .list();
    }

    public SessionRating updateRating (SessionRating updated) {
        updated.update();
        return updated;
    }

    public Optional<SessionRating> getByRatingId (String ratingId) {
        return SessionRating.find("ratingId", ratingId)
                .firstResultOptional();
    }

    public void delete (SessionRating rating) {
        rating.delete();
    }

    public Collection<SessionRating> getRatingsBySession (String sessionId) {
        return SessionRating.find("session", sessionId)
                .list();
    }

    public Collection<SessionRating> getRatingsByAttendee (String attendeeId) {
        return SessionRating.find("attendeeId", attendeeId)
                .list();
    }

    private static class RatingIdGenerator {

        public String nextValue () {
            return new ObjectId().toHexString();
        }

    }

}
