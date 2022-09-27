export enum ParkStatus {
    OPEN = "OPEN",
    CLOSED = "OPEN"
}

export interface Park {
    uuid: string;
    name: string;
    city: string;
    size: number;
    status: ParkStatus;
}

