package org.acme.conference.vote;

import javax.ws.rs.BadRequestException;

/**
 * Validates the attendee id and returns the attendee.
 *
 */
public class AttendeeRequestValidator {

    private AttendeeDAO attendeeDAO;

    public AttendeeRequestValidator(AttendeeDAO attendeeDAO) {
        this.attendeeDAO = attendeeDAO;
    }

    /**
     * Validates the attendee id is a valid Id.
     * 
     * @param attendeeId
     * @return
     * @throws BadRequestException
     */
    public Attendee validate (String attendeeId) {
        return attendeeDAO.get(attendeeId)
                .orElseThrow( () -> new BadRequestException("Invalid attendee id: " + attendeeId));
    }

}
