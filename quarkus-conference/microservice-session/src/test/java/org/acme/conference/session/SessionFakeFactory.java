package org.acme.conference.session;

public class SessionFakeFactory {

    public static final String DEFAULT_ID = "ID";

    public static final int DEFAULT_SCHEDULE = 101;

    public static Session composeSession () {
        Session session = new Session();
        session.id=DEFAULT_ID;
        return session;
    }

}
