import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { format } from "date-fns";
import {
    List,
    ListItem,
    PageSection,
    TextContent,
    TextList,
    TextListItem,
    TextListItemVariants,
    TextListVariants,
    Title,
} from "@patternfly/react-core";

import { LoadingContent } from "../Components/LoadingContent";
import { Schedule } from "../Models/Schedule";
import { Session } from "../Models/Session";

import * as scheduleService from "../Services/ScheduleService";
import * as sessionService from "../Services/SessionService";

export function ScheduleDetailPage(props: {
    match: { params: { scheduleId: string } };
}) {
    const [schedule, setSchedule] = useState<Schedule>();
    const [sessions, setSessions] = useState<Session[]>();

    useEffect(() => {
        scheduleService.get(props.match.params.scheduleId).then(setSchedule);
    }, [props.match.params]);

    useEffect(() => {
        // NOTE this is very inefficient for large numbers of total sessions
        sessionService.getAll().then((allSessions) => {
            const filteredSessions = allSessions.filter(
                (session) => session.schedule === schedule?.id
            );
            setSessions(filteredSessions);
        });
    }, [schedule]);

    if (schedule) {
        const formattedSessions = sessions?.map((session) => (
            <ListItem key={session.id}>
                <Link to={`/sessions/${session.id}`}>{session.id}</Link>
                <span>&nbsp;with&nbsp;</span>
                {session.speakers.map((speaker, index) => (
                    <React.Fragment key={speaker.id}>
                        {index > 0 && <span>,&nbsp;</span>}
                        <Link to={`/speakers/${speaker.uuid}`}>
                            {speaker.name}
                        </Link>
                        &nbsp;
                    </React.Fragment>
                ))}
            </ListItem>
        ));

        return (
            <PageSection>
                <Title headingLevel="h1">Schedule {schedule?.id}</Title>

                <TextContent>
                    <TextList component={TextListVariants.dl}>
                        <TextListItem component={TextListItemVariants.dt}>
                            Date
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {format(schedule.date, "PPPP")}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Start Time
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {schedule.startTime}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Venue
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            {schedule.venueId}
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dt}>
                            Sessions
                        </TextListItem>
                        <TextListItem component={TextListItemVariants.dd}>
                            <List>{formattedSessions}</List>
                        </TextListItem>
                    </TextList>
                </TextContent>
            </PageSection>
        );
    } else {
        return <LoadingContent />;
    }
}
