package org.acme.conference.vote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.MongoClient;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.mongo.MongoDatabaseTestResource;

/**
 * Test for the Request validator of Attendees.
 *
 */
@QuarkusTest
@QuarkusTestResource(MongoDatabaseTestResource.class)
public class AttendeeRequestValidatorTest {

    private static final String ATTENDEE_ID = "ATT-VALID-UUID";
    private static final String ATTENDEE_VALID_NAME = "ATT-VALID-NAME";

    private AttendeeRequestValidator validator;

    @Inject
    private AttendeeDAO attendeeDAO;

    @Inject
    private MongoClient mongoClient;

    private AttendeeDataForgery attendeeForge;

    private Attendee attendee;

    @BeforeEach
    public void setUp () {
        validator = new AttendeeRequestValidator(attendeeDAO);
        attendeeForge = new AttendeeDataForgery(mongoClient);
        attendee = attendeeForge.create(ATTENDEE_ID, ATTENDEE_VALID_NAME);
    }

    @AfterEach
    public void tearDown () {
        attendeeForge.deleteAll();
    }

    /**
     * Test method for {@link org.acme.conference.vote.AttendeeRequestValidator#validate(java.lang.String)}.
     */
    @Test
    public void testValidate () {

        Attendee other = validator.validate(ATTENDEE_ID);

        assertEquals(attendee.getId(), other.getId());
        assertEquals(attendee.getName(), other.getName());
    }

    @Test
    public void testValidateThrowsBadRequestException () {
        assertThrows(BadRequestException.class, () -> {
            validator.validate("ATT-INVALID-ID");
        });
    }
}
