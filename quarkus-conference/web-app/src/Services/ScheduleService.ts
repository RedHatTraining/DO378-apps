import { parseISO } from "date-fns";

import { Schedule } from "../Models/Schedule";
import { getRESTClient, ServiceName } from "./RESTClient";

const api = getRESTClient(ServiceName.SCHEDULE_SERVICE);

export interface RawScheduleData {
    id: number;
    venueId: number;
    startTime: string;
    date: string;
}

export async function get(id: string) {
    return api.url(`/schedule/${id}`).get().json<RawScheduleData>().then(asSchedule);
}

export async function getAll() {
    const scheduleData = await api.url("/schedule/all").get().json<RawScheduleData[]>();
    return scheduleData.map(asSchedule);
}

function asSchedule(rawSchedule: RawScheduleData): Schedule {
    return {
        ...rawSchedule,
        date: parseISO(rawSchedule.date),
    };
}
