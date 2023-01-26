import React, { useEffect, useState } from "react";
import { PageSection, Title } from "@patternfly/react-core";

import { SpeakerList } from "../Components/SpeakerList";
import { Speaker } from "../Models/Speaker";
import * as speakerService from "../Services/SpeakerService";

export function SpeakersPage() {
    const [speakers, setSpeakers] = useState<Speaker[]>();

    useEffect(() => {
        speakerService.listAllSorted("nameFirst").then(setSpeakers);
    }, []);

    return (
        <PageSection>
            <Title headingLevel="h1">Speakers</Title>
            {speakers && <SpeakerList speakers={speakers} />}
        </PageSection>
    );
}
