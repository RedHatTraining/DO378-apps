import { Session } from "../Models/Session";
import { getRESTClient, ServiceName } from "./RESTClient";

const api = getRESTClient(ServiceName.SESSION_SERVICE);

export async function get(id: string) {
    return api.url(`/${id}`).get().json<Session>();
}

export async function getAll() {
    return api.get().json<Session[]>();
}
