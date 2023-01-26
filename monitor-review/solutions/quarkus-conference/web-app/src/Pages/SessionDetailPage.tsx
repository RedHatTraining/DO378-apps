import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {
    Button,
    Form,
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

export function SessionDetailPage(this: any, props: {
    match: { params: { sessionId: string } };
}) {
    const [session, setSession] = useState<Session>();

    useEffect(() => {
        sessionService.get(props.match.params.sessionId).then(setSession);
    }, [props.match]);

    function submit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        sessionService.likeSession(props.match.params.sessionId);
    }

    if (session) {
        return (
            <Form onSubmit={submit}>
            <PageSection>
                <Title headingLevel="h1">
                    Session {session.id}
                </Title>
                <br/>
                <TextContent>
                    <TextList component={TextListVariants.dl}>
                        
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
                <br/>
                <Button type="submit" variant="primary">
                    I am interested
                </Button>
            </PageSection>
            </Form>
        );
    } else {
        return <LoadingContent />;
    }
}
