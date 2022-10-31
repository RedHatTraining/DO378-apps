package org.acme.conference.vote;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Resource to get Rating results and statistics.
 *
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class RatingStatsResource {

    private final SessionRatingDAO sessionRatingDAO;

    private final AttendeeRequestValidator attendeeValidator;

    @Inject
    public RatingStatsResource(SessionRatingDAO sessionRatingDAO, AttendeeDAO attendeeDAO) {
        this.sessionRatingDAO = sessionRatingDAO;
        this.attendeeValidator = new AttendeeRequestValidator(attendeeDAO);
    }

    @GET
    @Path("/ratingsBySession")
    public Collection<SessionRating> allSessionVotes (@QueryParam("sessionId") String sessionId) {
        return sessionRatingDAO.getRatingsBySession(sessionId);
    }

    @GET
    @Path("/averageRatingBySession")
    public double sessionRatingAverage (@QueryParam("sessionId") String sessionId) {
        return allSessionVotes(sessionId).stream()
                .map(SessionRating::getRating)
                .mapToDouble(Double::valueOf)
                .average()
                .orElse(0d);
    }

    @GET
    @Path("/ratingsByAttendee")
    public Collection<SessionRating> votesByAttendee (@QueryParam("attendeeId") String attendeeId) {
        attendeeValidator.validate(attendeeId);
        return sessionRatingDAO.getRatingsByAttendee(attendeeId);
    }


}
