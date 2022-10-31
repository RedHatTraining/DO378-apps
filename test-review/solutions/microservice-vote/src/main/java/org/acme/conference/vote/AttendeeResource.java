package org.acme.conference.vote;

import java.util.Collection;
import javax.enterprise.context.RequestScoped;
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
import javax.ws.rs.core.Response;

/**
 * Resource to manage Attendee requests.
 *
 * @author jzuriaga
 *
 */
@RequestScoped
@Path("attendee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AttendeeResource {

    @Inject
    private AttendeeDAO attendeeDAO;

    @POST
    public Attendee registerAttendee (Attendee attendee) {
        attendeeDAO.create(attendee);
        return attendee;
    }

    @PUT
    @Path("{id}")
    public Attendee updateAttendee (@PathParam("id") String id, Attendee attendee) {

        return attendeeDAO.update(id, attendee)
                .orElseThrow(NotFoundException::new);
    }

    @GET
    public Collection<Attendee> getAllAttendees () {
        return attendeeDAO.findAll();
    }

    @GET
    @Path("{id}")
    public Attendee getAttendee (@PathParam("id") String id) {
        return attendeeDAO.get(id)
                .orElseThrow(NotFoundException::new);
    }

    @DELETE
    @Path("{id}")
    public Response deleteAttendee (@PathParam("id") String id) {
        return attendeeDAO.get(id)
                .filter(attendeeDAO::delete)
                .map(a -> Response.noContent()
                        .build())
                .orElseThrow(NotFoundException::new);
    }
}
