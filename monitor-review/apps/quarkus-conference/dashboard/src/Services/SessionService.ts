import { Session } from "../Models/Session";
import { getRESTClient, ServiceName } from "./RESTClient";

const api = getRESTClient(ServiceName.SESSION_SERVICE);

export async function get(id: string) {
    return api.url(`/sessions/${id}`).get().json<Session>();
}

export async function getAll() {
    return api.url("/sessions").get().json<Session[]>();
}

export async function likeSession(id: string) {
    await api.url(`/sessions/subscribe/${id}`).post();
    alert('Thank you for your interest in this session!');
}
