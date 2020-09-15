import { Speaker } from "../Models/Speaker";
import { getRESTClient, ServiceName } from "./RESTClient";

const api = getRESTClient(ServiceName.SPEAKER_SERVICE);

export async function listAllSorted(sortBy: String) {
    return api.url(`/sorted?sort=${sortBy}`).get().json<Speaker[]>();
}

export async function findByUuid(uuid: String) {
    return api.url(`/${uuid}`).get().json<Speaker>();
}

export async function create(speaker: Omit<Speaker, "id">): Promise<Speaker> {
    return api.post(speaker).json<Speaker>();
}
