import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
    PageSection,
    Title,
    List,
    ListItem,
    TextContent,
    TextList,
    TextListVariants,
    TextListItem,
    TextListItemVariants,
} from "@patternfly/react-core";

import { Session } from "../Models/Session";
import { LoadingContent } from "../Components/LoadingContent";
import * as sessionService from "../Services/SessionService";
import * as voteService from "../Services/VoteService";
import { StarRating } from "../Components/StarRating";

export function SessionDetailPage(props: {
    match: { params: { sessionId: string } };
}) {
    const [session, setSession] = useState<Session>();
    const [averageRating, setAverageRating] = useState<number>();

    // "form" data
    const [rating, setRating] = useState<number>(0);

    useEffect(() => {
        sessionService.get(props.match.params.sessionId).then(setSession);
    }, [props.match]);

    useEffect(() => {
        voteService
            .getAverageRatingBySession(props.match.params.sessionId)
            .then(setAverageRating);
    }, [props.match, averageRating]);

    if (session) {
        return (
            <PageSection>
                <Title headingLevel="h1">
                    Session {session.id}
                    <StarRating
                        rating={rating}
                        max={5}
                        onRatingChange={(newRating) => {
                            setRating(newRating);
                            voteService
                                .rateSession({
                                    attendeeId: "TODO: some attendee ID",
                                    rating,
                                    session: props.match.params.sessionId,
                                })
                                .then(() => setAverageRating(undefined));
                        }}
                    />
                </Title>

                <TextContent>
                    <TextList component={TextListVariants.dl}>
                        <TextListItem component={TextListItemVariants.dt}>
                            Rating
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {averageRating}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Schedule
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            <Link to={`/schedules/${session.schedule}`}>
                                {session.schedule}
                            </Link>
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Speakers
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            <List>
                                {session.speakers.map((speaker) => (
                                    <ListItem key={speaker.id}>
                                        <Link to={`/speakers/${speaker.uuid}`}>
                                            {speaker.name}
                                        </Link>
                                    </ListItem>
                                ))}
                            </List>
                        </TextListItem>
                    </TextList>
                </TextContent>
            </PageSection>
        );
    } else {
        return <LoadingContent />;
    }
}
