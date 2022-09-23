import { Park } from "@app/models/Park";
import { getRESTClient, ServiceName } from "./API";

const API = getRESTClient(ServiceName.BACKEND);

export function all(): Promise<Park[]> {
    return API.url("parks")
        .get().json<Park[]>();
}
