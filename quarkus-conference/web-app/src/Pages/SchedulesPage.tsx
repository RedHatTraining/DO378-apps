import React, { useState, useEffect } from "react";
import { PageSection, Title } from "@patternfly/react-core";

import * as scheduleService from "../Services/ScheduleService";
import { Schedule } from "../Models/Schedule";
import { ScheduleList } from "../Components/ScheduleList";

export function SchedulesPage() {
    const [schedules, setSchedules] = useState<Schedule[]>();
    useEffect(() => {
        scheduleService.getAll().then(setSchedules);
    }, []);
    return (
        <PageSection>
            <Title headingLevel="h1">Schedules</Title>
            {schedules && <ScheduleList schedules={schedules}></ScheduleList>}
        </PageSection>
    );
}
