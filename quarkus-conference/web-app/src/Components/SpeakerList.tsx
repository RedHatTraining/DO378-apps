import React from "react";
import { List } from "@patternfly/react-core";

import { Speaker } from "../Models/Speaker";

export function SpeakerList(props: { speakers: Speaker[] }) {
    return (
        <List>
            {props.speakers.map((speaker) => (
                <div key={`speaker_${speaker.id}`} className="speaker">
                    <a href={`/speakers/${speaker.uuid}`}>
                        {speaker.nameFirst} {speaker.nameLast}
                    </a>
                </div>
            ))}
        </List>
    );
}
