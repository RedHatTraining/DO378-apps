import { Park } from "@app/models/Park";
import { getRESTClient, ServiceName } from "./API";

const API = getRESTClient(ServiceName.BACKEND);

export function all(): Promise<Park[]> {
    return API.url("parks")
        .get().json<Park[]>();
}

export function update(park: Park): Promise<void> {
    return API.url("parks").put(park);
}