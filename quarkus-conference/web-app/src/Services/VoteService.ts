import { getRESTClient, ServiceName } from "./RESTClient";
import { SessionRating } from "../Models/SessionRating";

// TODO: add service URL (mechanism)
const api = getRESTClient(ServiceName.VOTE_SERVICE);

export async function rateSession(rating: SessionRating) {
    return api.url("/rate").post(rating).json<SessionRating>();
}

export async function getAverageRatingBySession(sessionId: string) {
    const rating = await api
        .url(`/averageRatingBySession?sessionId=${sessionId}`)
        .get()
        .json<number | { value: number }>();
    return typeof rating === "object" ? rating.value : rating;
}
