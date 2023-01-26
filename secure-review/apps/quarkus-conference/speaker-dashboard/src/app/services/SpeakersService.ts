import { Speaker } from "../models/Speaker";
import { getRESTClient, ServiceName } from "./API";

const API = getRESTClient(ServiceName.SPEAKER_BACKEND);

export function all(token: string): Promise<Speaker[]> {
    return API.url("speakers")
        .auth(`Bearer ${token}`)
        .get()
        .json<Speaker[]>();
}

export function create(token: string, speaker: Speaker): Promise<Speaker[]> {
    return API.url("speakers")
        .auth(`Bearer ${token}`)
        .post(speaker)
        .json<Speaker[]>();
}
