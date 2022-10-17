import { Park, ParkStatus } from "@app/models/Park";
import { getRESTClient, ServiceName } from "./API";
import { getToken } from "./AuthService";

const API = getRESTClient(ServiceName.PARKS_BACKEND);

export function all(): Promise<Park[]> {
    return API.url("parks")
        .get()
        .json<Park[]>();
}

export function open(park: Park): Promise<void> {
    return update({ ...park, status: ParkStatus.OPEN });
}

export function close(park: Park): Promise<void> {
    return update({ ...park, status: ParkStatus.CLOSED });
}

export function checkWeather(park: Park): Promise<void> {
    return API.url(`parks/${park.id}/weathercheck`)
        .post()
        .text();
}

export function update(park: Park): Promise<void> {
    return API.url("parks")
        .auth(`Bearer ${getToken()}`)
        .put(park)
        .unauthorized(error => {
            throw new Error(
                "You must be logged in as an admin to perform this action. " +
                `(${error.response.status} ${error.response.statusText})`);
        })
        .text();
}