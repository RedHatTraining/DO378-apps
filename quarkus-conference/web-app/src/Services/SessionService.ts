import { Session } from "../Models/Session";
import { getRESTClient, ServiceName } from "./RESTClient";

const api = getRESTClient(ServiceName.SESSION_SERVICE);

export async function get(id: string) {
    return api.url(`/sessions/${id}`).get().json<Session>();
}

export async function getAll() {
    return api.url("/sessions").get().json<Session[]>();
}
