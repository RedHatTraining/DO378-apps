import React from "react";
import {
    Card, CardTitle, CardBody,
    DescriptionList, DescriptionListGroup,
    DescriptionListTerm, DescriptionListDescription
} from "@patternfly/react-core";
import { Speaker } from "@app/models/Speaker";
import SpeakIcon from "@patternfly/react-icons/dist/esm/icons/speakap-icon";

const icons = [
    <SpeakIcon key="speaker-icon-1" size="sm" color="#aaaa22" />
];

interface SpeakerCardProps {
    speaker: Speaker,
}

export function SpeakerCard({ speaker }: SpeakerCardProps): JSX.Element {

    return(<Card isFlat>

        <CardTitle>{getSpeakerIcon(speaker)}{" "}{speaker.nameFirst}{" "}</CardTitle>
        <CardBody>
            <DescriptionList>
                <DescriptionListGroup>
                    <DescriptionListTerm>Last Name</DescriptionListTerm>
                    <DescriptionListDescription>{speaker.nameLast}</DescriptionListDescription>
                </DescriptionListGroup>
            </DescriptionList>
        </CardBody>
        </Card>
    );
}

function getSpeakerIcon(speaker: Speaker) {
    return icons[speaker.id - 1] || <SpeakIcon key="speaker-icon-1" size="md" color="#ffaa22" />
}
