package org.acme.conference.vote;

import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("rate")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    private final SessionRatingDAO sessionRatingDAO;

    private final AttendeeRequestValidator attendeeValidator;

    @Inject
    public RatingResource(final SessionRatingDAO sessionRatingDAO, final AttendeeDAO attendeeDAO) {
        this.sessionRatingDAO = sessionRatingDAO;
        attendeeValidator = new AttendeeRequestValidator(attendeeDAO);
    }

    @POST
    public SessionRating rateSession (SessionRating sessionRating) {
        String attendeeId = sessionRating.getAttendeeId();

        attendeeValidator.validate(attendeeId);

        SessionRating rating = sessionRatingDAO.rateSession(sessionRating);
        return rating;
    }

    @GET
    public Collection<SessionRating> getAllSessionRatings () {
        return sessionRatingDAO.getAllRatings();
    }

    @PUT
    @Path("{ratingId}")
    public SessionRating updateRating (@PathParam("ratingId") String ratingId, SessionRating newRating) {
        SessionRating original = getRating(ratingId);
        original.setSession(newRating.getSession());
        original.setRating(newRating.getRating());
        Attendee attendee = attendeeValidator.validate(newRating.getAttendeeId());
        original.setAttendeeId(attendee.getId());

        SessionRating updated = sessionRatingDAO.updateRating(original);

        return updated;
    }

    @GET
    @Path("{ratingId}")
    public SessionRating getRating (@PathParam("ratingId") String ratingId) {
        return sessionRatingDAO.getByRatingId(ratingId)
                .orElseThrow( () -> new NotFoundException("Rating not found: " + ratingId));
    }

    @DELETE
    @Path("{ratingId}")
    public void deleteRating (@PathParam("ratingId") String ratingId) {
        SessionRating rating = getRating(ratingId);
        sessionRatingDAO.delete(rating);
    }

}
