package com.redhat.training.conference.session;

import static com.redhat.training.conference.session.SessionFakeFactory.DEFAULT_SCHEDULE;
import static com.redhat.training.conference.session.SessionFakeFactory.composeSession;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

/**
 * SessionStore
 */
@QuarkusTest
public class SessionStoreTest {

    @Inject
    private SessionStore store;

    @Test
    public void testPersist () {
        Session session = new Session();
        session.id="my.test.session";

        Session result = store.save(session);

        assertNotNull(result);
        assertEquals("my.test.session", result.id);
    }

}
