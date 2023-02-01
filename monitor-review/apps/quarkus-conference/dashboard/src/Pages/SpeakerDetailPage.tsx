import React, { useEffect, useState } from "react";
import {
    PageSection,
    TextContent,
    TextList,
    TextListItem,
    TextListItemVariants,
    TextListVariants,
    Title,
} from "@patternfly/react-core";

import { Speaker } from "../Models/Speaker";
import { LoadingContent } from "../Components/LoadingContent";
import * as speakerService from "../Services/SpeakerService";

export function SpeakerDetailPage(props: {
    match: { params: { speakerId: string } };
}) {
    const [speaker, setSpeaker] = useState<Speaker>();

    useEffect(() => {
        speakerService
            .findByUuid(props.match.params.speakerId)
            .then(setSpeaker);
    }, [props.match]);

    if (speaker) {
        return (
            <PageSection>
                <Title headingLevel="h1">
                    {speaker.nameFirst} {speaker.nameLast}
                    &nbsp;
                </Title>

                <TextContent>
                    <TextList component={TextListVariants.dl}>
                        <TextListItem component={TextListItemVariants.dt}>
                            Organization
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {speaker.organization}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Twitter
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {speaker.twitterHandle}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Biography
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {speaker.biography}
                        </TextListItem>
                    </TextList>
                </TextContent>
            </PageSection>
        );
    } else {
        return <LoadingContent></LoadingContent>;
    }
}
