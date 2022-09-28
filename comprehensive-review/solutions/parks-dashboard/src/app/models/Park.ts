export enum ParkStatus {
    OPEN = "OPEN",
    CLOSED = "CLOSED"
}

export interface Park {
    uuid: string;
    name: string;
    city: string;
    size: number;
    status: ParkStatus;
}

