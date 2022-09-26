import { Park } from "@app/models/Park";
import { getRESTClient, ServiceName } from "./API";
import { getToken } from "./AuthService";

const API = getRESTClient(ServiceName.BACKEND);

export function all(): Promise<Park[]> {
    return API.url("parks")
        .get().json<Park[]>();
}

export function update(park: Park): Promise<void> {
    return API.url("parks")
        .auth(`Bearer ${getToken()}`)
        .put(park);
}