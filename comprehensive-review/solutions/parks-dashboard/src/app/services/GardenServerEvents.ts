import { GardenEvent } from "@app/models/GardenEvent";
import { SensorMeasurement } from "@app/models/SensorMeasurement";
import { GardenStatus } from "../models/GardenStatus";
import { ServiceName, getSSEClient } from "./API";

const sse = getSSEClient(ServiceName.BACKEND);

export function subscribeToGardenStatuses(onEvent: (status: GardenStatus) => void): void {
    sse.open<GardenStatus>("/garden/statuses", onEvent);
}


interface ApiGardenEvent {
    name: string,
    gardenName: string,
    value: number,
    sensorId: number,
    timestamp: number
}

export function subscribeToGardenTemperatureEvents(onEvent: (e: GardenEvent) => void): void {
    sse.open<ApiGardenEvent>("/garden/events/temperature", (receivedEvent: ApiGardenEvent) => {
        const measurement = {
            ...receivedEvent,
            timestamp: new Date(receivedEvent.timestamp)
        };
        onEvent(measurement);
    });
}
export function subscribeToGardenHumidityEvents(onEvent: (e: GardenEvent) => void): void {
    sse.open<ApiGardenEvent>("/garden/events/humidity", (receivedEvent: ApiGardenEvent) => {
        const measurement = {
            ...receivedEvent,
            timestamp: new Date(receivedEvent.timestamp)
        };
        onEvent(measurement);
    });
}

export function subscribeToGardenWindEvents(onEvent: (e: GardenEvent) => void): void {
    sse.open<ApiGardenEvent>("/garden/events/wind", (receivedEvent: ApiGardenEvent) => {
        const measurement = {
            ...receivedEvent,
            timestamp: new Date(receivedEvent.timestamp)
        };
        onEvent(measurement);
    });
}

interface ApiSensorMeasurement {
    type: string,
    value: any,
    gardenName: string,
    sensorName: string,
    timestamp: number
}

export function subscribeToSensorMeasurements(onEvent: (m: SensorMeasurement) => void): void {
    sse.open<ApiSensorMeasurement>("/sensor/measurements/enriched", (receivedMeasurement: ApiSensorMeasurement) => {
        const measurement = {
            ...receivedMeasurement,
            timestamp: new Date(receivedMeasurement.timestamp)
        };
        onEvent(measurement);
    });
}
