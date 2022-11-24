import React, { useState, useEffect } from "react";
import { PageSection, Title } from "@patternfly/react-core";

import { Session } from "../Models/Session";
import { SessionList } from "../Components/SessionList";
import * as sessionService from "../Services/SessionService";

export function SessionsPage() {
    const [sessions, setSessions] = useState<Session[]>();

    useEffect(() => {
        sessionService.getAll().then(setSessions);
    }, []);

    return (
        <PageSection>
            <Title headingLevel="h1">Sessions</Title>
            {sessions && <SessionList sessions={sessions} />}
        </PageSection>
    );
}
