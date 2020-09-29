import React from "react";
import { Link } from "react-router-dom";
import { List } from "@patternfly/react-core";

import { Schedule } from "../Models/Schedule";

export function ScheduleList(props: { schedules: Schedule[] }) {
    return (
        <List>
            {props.schedules.map((schedule) => (
                <div key={schedule.id} className="session">
                    <Link to={`/schedules/${schedule.id}`}>
                        Schedule {schedule.id}
                    </Link>
                </div>
            ))}
        </List>
    );
}
