import { ParkEvent } from "@app/models/ParkEvent";
import { SensorMeasurement } from "@app/models/SensorMeasurement";
import { ParkStatus } from "../models/ParkStatus";
import { ServiceName, getSSEClient } from "./API";

const sse = getSSEClient(ServiceName.BACKEND);

export function subscribeToGardenStatuses(onEvent: (status: ParkStatus) => void): void {
    sse.open<ParkStatus>("/garden/statuses", onEvent);
}


interface ApiParkEvent {
    name: string,
    gardenName: string,
    value: number,
    sensorId: number,
    timestamp: number
}

export function subscribeToGardenTemperatureEvents(onEvent: (e: ParkEvent) => void): void {
    sse.open<ApiParkEvent>("/garden/events/temperature", (receivedEvent: ApiParkEvent) => {
        const measurement = {
            ...receivedEvent,
            timestamp: new Date(receivedEvent.timestamp)
        };
        onEvent(measurement);
    });
}
export function subscribeToGardenHumidityEvents(onEvent: (e: ParkEvent) => void): void {
    sse.open<ApiParkEvent>("/garden/events/humidity", (receivedEvent: ApiParkEvent) => {
        const measurement = {
            ...receivedEvent,
            timestamp: new Date(receivedEvent.timestamp)
        };
        onEvent(measurement);
    });
}

export function subscribeToGardenWindEvents(onEvent: (e: ParkEvent) => void): void {
    sse.open<ApiParkEvent>("/garden/events/wind", (receivedEvent: ApiParkEvent) => {
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
