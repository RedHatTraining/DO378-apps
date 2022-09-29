import { WeatherWarning } from "@app/models/WeatherWarning";
import { getRESTClient, ServiceName } from "./API";

const API = getRESTClient(ServiceName.WEATHER_BACKEND);


export function all(): Promise<WeatherWarning[]> {
    return API.url("warnings")
        .get()
        .json<WeatherWarning[]>();
}


export function simulateWarnings(): Promise<void> {
    return API.url("warnings/simulation")
        .post()
        .text();
}