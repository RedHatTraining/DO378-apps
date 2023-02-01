import React from "react";
import { List } from "@patternfly/react-core";

import { Session } from "../Models/Session";

export function SessionList(props: { sessions: Session[] }) {
    return (
        <List>
            {props.sessions.map((session) => (
                <div key={session.id} className="session">
                    <a href={`/sessions/${session.id}`}>Session {session.id}</a>
                </div>
            ))}
        </List>
    );
}
